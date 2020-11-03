package com.lucasrodrigues.myfinances.view_model

import androidx.lifecycle.MutableLiveData
import com.lucasrodrigues.myfinances.framework.AlertService
import com.lucasrodrigues.myfinances.framework.NavigationService
import com.lucasrodrigues.myfinances.interactor.Login
import com.lucasrodrigues.myfinances.model.LoadingState

class LoginViewModel(
    alertService: AlertService,
    navigationService: NavigationService,
    private val login: Login
) : BaseViewModel(alertService, navigationService) {

    val email = MutableLiveData("")
    val password = MutableLiveData("")

    val loggingIn = MutableLiveData<LoadingState>(LoadingState.Idle)

    fun tryLogin() {
        request(
            call = { login(Login.Params(email.value ?: "", password.value ?: "")) },
            loadingState = loggingIn,
            onException = {
                alertService.sendErrorAlert(it)
            }
        ) {
            navigationService.goToMain()
        }
    }

    fun register() {
        navigationService.goToRegister()
    }
}