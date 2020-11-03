package com.lucasrodrigues.myfinances.view_model

import com.lucasrodrigues.myfinances.framework.AlertService
import com.lucasrodrigues.myfinances.framework.NavigationService
import com.lucasrodrigues.myfinances.interactor.HasLoggedUser

class SplashViewModel(
    alertService: AlertService,
    navigationService: NavigationService,
    private val hasLoggedUser: HasLoggedUser
) : BaseViewModel(alertService, navigationService) {
    fun checkUserSession() {
        request(call = { hasLoggedUser() }) {
            if (it) {
                navigationService.goToMain()
            } else {
                navigationService.goToLogin()
            }
        }
    }
}