package com.lucasrodrigues.myfinances.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import com.lucasrodrigues.myfinances.framework.AlertService
import com.lucasrodrigues.myfinances.framework.NavigationService
import com.lucasrodrigues.myfinances.interactor.ObserveExpenses
import com.lucasrodrigues.myfinances.interactor.ObserveRevenues
import com.lucasrodrigues.myfinances.model.FinancialEntry
import com.lucasrodrigues.myfinances.utils.Constants
import java.text.SimpleDateFormat
import java.util.*

class ListEntriesViewModel(
    alertService: AlertService,
    navigationService: NavigationService,
    entryType: Constants.EntryType,
    private val observeExpenses: ObserveExpenses,
    private val observeRevenues: ObserveRevenues
) : BaseViewModel(alertService, navigationService) {

    val entryType = MutableLiveData(entryType)

    val currentDate = MutableLiveData(Calendar.getInstance())

    val entries = Transformations.switchMap(currentDate) {
        val month = it.get(Calendar.MONTH)
        val year = it.get(Calendar.YEAR)

        if (entryType == Constants.EntryType.EXPENSE) {
            observeExpenses(ObserveExpenses.Params(month, year)).asLiveData()
        } else {
            observeRevenues(ObserveRevenues.Params(month, year)).asLiveData()
        }
    }

    val entrySum = Transformations.map(entries) {
        it.fold(0.0) { acc, financialEntry ->
            acc + financialEntry.value
        }
    }

    val pendingSum = Transformations.map(entries) {
        it.filter { financialEntry ->
            !financialEntry.consummate
        }.fold(0.0) { acc, financialEntry ->
            acc + financialEntry.value
        }
    }

    fun editEntry(entry: FinancialEntry) {
        navigationService.goToEditEntry(entry)
    }

    fun nextMonth() {
        val calendar = currentDate.value?.apply {
            add(Calendar.MONTH, 1)
        }

        currentDate.value = calendar
    }

    fun previousMonth() {
        val calendar = currentDate.value?.apply {
            add(Calendar.MONTH, -1)
        }

        currentDate.value = calendar
    }
}