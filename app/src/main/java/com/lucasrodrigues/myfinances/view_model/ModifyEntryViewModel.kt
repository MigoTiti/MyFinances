package com.lucasrodrigues.myfinances.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.lucasrodrigues.myfinances.framework.AlertService
import com.lucasrodrigues.myfinances.framework.DialogService
import com.lucasrodrigues.myfinances.framework.NavigationService
import com.lucasrodrigues.myfinances.interactor.CreateEntry
import com.lucasrodrigues.myfinances.interactor.DeleteEntry
import com.lucasrodrigues.myfinances.interactor.UpdateEntry
import com.lucasrodrigues.myfinances.model.FinancialEntry

class ModifyEntryViewModel(
    alertService: AlertService,
    navigationService: NavigationService,
    private val dialogService: DialogService,
    initialEntry: FinancialEntry,
    private val createEntry: CreateEntry,
    private val updateEntry: UpdateEntry,
    private val deleteEntry: DeleteEntry
) : BaseViewModel(alertService, navigationService) {

    private val initialEntry = MutableLiveData(initialEntry)

    val exists = Transformations.map(this.initialEntry) {
        it.id != null
    }

    val isExpense = Transformations.map(this.initialEntry) {
        it is FinancialEntry.Expense
    }

    val description = MutableLiveData(initialEntry.description)
    val value = MutableLiveData(initialEntry.value.toString())
    val date = MutableLiveData(initialEntry.date)
    val consummate = MutableLiveData(initialEntry.consummate)

    fun pickDate() {
        request(call = { dialogService.pickDate(date.value!!) }) {
            date.postValue(it)
        }
    }

    private fun validate(): Boolean {
        val value = value.value
        val date = date.value

        if (value == null || value.isBlank()) {
            alertService.sendErrorAlert("Digite um valor válido")
            return false
        }

        try {
            val numberValue = value.toDouble()

            if (numberValue == 0.0) {
                alertService.sendErrorAlert("Digite um valor maior que 0")
                return false
            }
        } catch (e: Exception) {
            alertService.sendErrorAlert("Digite um valor válido")
            return false
        }

        if (date == null) {
            alertService.sendErrorAlert("Escolha uma data válida")
            return false
        }

        return true
    }

    fun tryConfirm() {
        if (validate()) {
            val entry: FinancialEntry = when (val initialEntry = initialEntry.value!!) {
                is FinancialEntry.Expense -> initialEntry.copy(
                    description = description.value!!,
                    value = value.value!!.toDouble(),
                    consummate = consummate.value!!,
                    date = date.value!!
                )
                else -> (initialEntry as FinancialEntry.Revenue).copy(
                    description = description.value!!,
                    value = value.value!!.toDouble(),
                    consummate = consummate.value!!,
                    date = date.value!!
                )
            }

            if (exists.value!!) {
                request(
                    call = { updateEntry(UpdateEntry.Params(entry)) },
                    onLoadBegin = {
                        alertService.showLoadingAlert("Editando")
                    }
                ) {
                    alertService.hideLoadingAlert()
                    navigationService.goBack()
                }
            } else {
                request(
                    call = { createEntry(CreateEntry.Params(entry)) },
                    onLoadBegin = {
                        alertService.showLoadingAlert("Criando")
                    }
                ) {
                    alertService.hideLoadingAlert()
                    navigationService.goBack()
                }
            }
        }
    }

    fun delete() {
        request(
            call = { deleteEntry(DeleteEntry.Params(initialEntry.value!!)) },
            onLoadBegin = {
                alertService.showLoadingAlert("Removendo")
            }
        ) {
            alertService.hideLoadingAlert()
            navigationService.goBack()
        }
    }
}