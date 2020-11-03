package com.lucasrodrigues.myfinances.view_model

import androidx.lifecycle.MutableLiveData
import com.lucasrodrigues.myfinances.framework.AlertService
import com.lucasrodrigues.myfinances.framework.NavigationService
import com.lucasrodrigues.myfinances.interactor.Register
import com.lucasrodrigues.myfinances.model.LoadingState

class RegisterViewModel(
    alertService: AlertService,
    navigationService: NavigationService,
    private val register: Register
) : BaseViewModel(alertService, navigationService) {

    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val passwordConfirmation = MutableLiveData("")
    val name = MutableLiveData("")

    val registering = MutableLiveData<LoadingState>(LoadingState.Idle)

    private fun validate(): Boolean {
        val email = email.value
        val password = password.value
        val passwordConfirmation = passwordConfirmation.value
        val name = name.value

        if (email.isNullOrBlank()) {
            alertService.sendErrorAlert("Digite seu email")
            return false
        }

        if (name.isNullOrBlank()) {
            alertService.sendErrorAlert("Digite seu nome")
            return false
        }

        if (password.isNullOrBlank()) {
            alertService.sendErrorAlert("Digite sua senha")
            return false
        }

        if (passwordConfirmation != password) {
            alertService.sendErrorAlert("A senha e a confirmação devem ser iguais")
            return false
        }

        return true
    }

    fun tryRegister() {
        if (validate())
            request(
                call = {
                    register(
                        Register.Params(
                            email.value!!,
                            password.value!!,
                            name.value!!
                        )
                    )
                },
                loadingState = registering,
                onException = {
                    alertService.sendErrorAlert(it)
                }
            ) {
                navigationService.goToMain()
            }
    }
}