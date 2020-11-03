package com.lucasrodrigues.myfinances.extensions

import com.google.firebase.firestore.DocumentSnapshot
import com.lucasrodrigues.myfinances.model.FinancialEntry
import kotlin.math.absoluteValue

fun DocumentSnapshot.toFinancialEntry(): FinancialEntry {
    val value = getDouble("value")!!

    return if (value < 0)
        FinancialEntry.Expense(
            id = id,
            value = value.absoluteValue,
            date = getTimestamp("date")!!.toDate(),
            consummate = getBoolean("consummate")!!,
            description = getString("description")!!
        )
    else
        FinancialEntry.Revenue(
            id = id,
            value = value.absoluteValue,
            date = getTimestamp("date")!!.toDate(),
            consummate = getBoolean("consummate")!!,
            description = getString("description")!!
        )
}