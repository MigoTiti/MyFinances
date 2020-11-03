package com.lucasrodrigues.myfinances.repository

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.SetOptions
import com.lucasrodrigues.myfinances.extensions.toFinancialEntry
import com.lucasrodrigues.myfinances.model.FinancialEntry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FinancialEntryRepository(
    private val firestore: FirebaseFirestore,
    private val authRepository: AuthRepository
) {

    private var userBalanceListener: ListenerRegistration? = null
    private var entriesRangeListener: ListenerRegistration? = null

    private fun getRange(month: Int, year: Int): Pair<Timestamp, Timestamp> {
        val begin = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, this.getActualMinimum(Calendar.DAY_OF_MONTH))
        }

        val end = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, this.getActualMaximum(Calendar.DAY_OF_MONTH))
        }

        val beginTimestamp = Timestamp(begin.time)
        val endTimestamp = Timestamp(end.time)

        return Pair(beginTimestamp, endTimestamp)
    }

    @ExperimentalCoroutinesApi
    fun observeExpenses(month: Int, year: Int): Flow<List<FinancialEntry.Expense>> {
        clearEntriesListener()

        val (begin, end) = getRange(month, year)

        return callbackFlow {
            entriesRangeListener =
                financialEntriesCollection()
                    ?.whereGreaterThan("date", begin)
                    ?.whereLessThan("date", end)
                    ?.addSnapshotListener { value, error ->
                        if (error == null) {
                            val entries: List<FinancialEntry.Expense> = (value?.documents?.map {
                                it.toFinancialEntry()
                            }
                                ?.filterIsInstance<FinancialEntry.Expense>() ?: listOf())

                            this@callbackFlow.offer(entries)
                        }
                    }

            awaitClose { clearEntriesListener() }
        }
    }

    @ExperimentalCoroutinesApi
    fun observeRevenues(month: Int, year: Int): Flow<List<FinancialEntry.Revenue>> {
        clearEntriesListener()

        val (begin, end) = getRange(month, year)

        return callbackFlow {
            entriesRangeListener =
                financialEntriesCollection()
                    ?.whereGreaterThan("date", begin)
                    ?.whereLessThan("date", end)
                    ?.addSnapshotListener { value, error ->
                        if (error == null) {
                            val entries: List<FinancialEntry.Revenue> = (value?.documents?.map {
                                it.toFinancialEntry()
                            }
                                ?.filterIsInstance<FinancialEntry.Revenue>() ?: listOf())

                            this@callbackFlow.offer(entries)
                        }
                    }

            awaitClose { clearEntriesListener() }
        }
    }

    @ExperimentalCoroutinesApi
    fun observeUserBalance(): Flow<Double> {
        clearUserBalanceListener()

        return callbackFlow {
            userBalanceListener =
                financialEntriesCollection()
                    ?.whereEqualTo("consummate", true)
                    ?.addSnapshotListener { value, error ->
                        if (error == null) {
                            val sum = value?.documents?.fold(0.0) { sum, documentSnapshot ->
                                sum + (documentSnapshot.data?.get("value") as Number).toDouble()
                            } ?: 0.0

                            this@callbackFlow.offer(sum)
                        }
                    }

            awaitClose { clearUserBalanceListener() }
        }
    }

    suspend fun createEntry(entry: FinancialEntry) {
        suspendCoroutine<Unit> { continuation ->
            val collection = financialEntriesCollection()

            if (collection != null) {
                collection
                    .document()
                    .set(
                        hashMapOf(
                            "description" to entry.description,
                            "consummate" to entry.consummate,
                            "value" to if (entry is FinancialEntry.Expense) -entry.value else entry.value,
                            "date" to Timestamp(entry.date)
                        )
                    )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            continuation.resume(Unit)
                        } else {
                            continuation.resumeWithException(it.exception ?: Exception())
                        }
                    }
            } else {
                continuation.resumeWithException(Exception())
            }
        }
    }

    suspend fun updateEntry(entry: FinancialEntry) {
        suspendCoroutine<Unit> { continuation ->
            val collection = financialEntriesCollection()

            if (collection != null) {
                collection
                    .document(entry.id!!)
                    .set(
                        hashMapOf(
                            "description" to entry.description,
                            "consummate" to entry.consummate,
                            "value" to if (entry is FinancialEntry.Expense) -entry.value else entry.value,
                            "date" to Timestamp(entry.date)
                        ),
                        SetOptions.merge()
                    )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            continuation.resume(Unit)
                        } else {
                            continuation.resumeWithException(it.exception ?: Exception())
                        }
                    }
            } else {
                continuation.resumeWithException(Exception())
            }
        }
    }

    suspend fun deleteEntry(entry: FinancialEntry) {
        suspendCoroutine<Unit> { continuation ->
            val collection = financialEntriesCollection()

            if (collection != null) {
                collection
                    .document(entry.id!!)
                    .delete()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            continuation.resume(Unit)
                        } else {
                            continuation.resumeWithException(it.exception ?: Exception())
                        }
                    }
            } else {
                continuation.resumeWithException(Exception())
            }
        }
    }

    private fun userDocument() = if (authRepository.hasLoggedUser())
        firestore
            .collection("users")
            .document(authRepository.getLoggedUser()!!.id)
    else
        null

    private fun financialEntriesCollection() = userDocument()?.collection("entries")

    private fun clearUserBalanceListener() {
        userBalanceListener?.remove()
        userBalanceListener = null
    }

    private fun clearEntriesListener() {
        entriesRangeListener?.remove()
        entriesRangeListener = null
    }

    private fun clear() {
        clearUserBalanceListener()
    }

    fun reset() {
        clear()
    }
}