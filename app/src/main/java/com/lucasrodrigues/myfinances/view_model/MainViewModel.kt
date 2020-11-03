package com.lucasrodrigues.myfinances.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.lucasrodrigues.myfinances.framework.AlertService
import com.lucasrodrigues.myfinances.framework.NavigationService
import com.lucasrodrigues.myfinances.interactor.GetLoggedUser
import com.lucasrodrigues.myfinances.interactor.Logout
import com.lucasrodrigues.myfinances.interactor.ObserveUserBalance
import com.lucasrodrigues.myfinances.model.User

class MainViewModel(
    alertService: AlertService,
    navigationService: NavigationService,
    observeUserBalance: ObserveUserBalance,
    getLoggedUser: GetLoggedUser,
    private val logout: Logout
) : BaseViewModel(alertService, navigationService) {

    val userBalance = observeUserBalance().asLiveData()
    val loggedUser = MutableLiveData<User>(getLoggedUser())

    fun addExpense() {
        navigationService.goToAddExpense()
    }

    fun addRevenue() {
        navigationService.goToAddRevenue()
    }

    fun listExpenses() {
        navigationService.goToListExpenses()
    }

    fun listRevenues() {
        navigationService.goToListRevenues()
    }

    fun tryLogout() {
        logout()
        navigationService.goToLogin()
    }
}