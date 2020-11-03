package com.lucasrodrigues.myfinances.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucasrodrigues.myfinances.framework.AlertService
import com.lucasrodrigues.myfinances.framework.NavigationService
import com.lucasrodrigues.myfinances.model.LoadingState
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    val alertService: AlertService,
    val navigationService: NavigationService
) : ViewModel() {

    fun <T> request(
        loadingState: MutableLiveData<LoadingState>? = null,
        onException: (Throwable) -> Unit = {},
        finally: () -> Unit = {},
        onLoadBegin: () -> Unit = {},
        call: suspend () -> T,
        onSuccess: (T) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                onLoadBegin()
                loadingState?.postValue(LoadingState.Active)

                onSuccess(call())
            } catch (e: Exception) {
                onException(e)
                loadingState?.postValue(LoadingState.Error(e))
            } finally {
                finally()
                loadingState?.postValue(LoadingState.Idle)
            }
        }
    }

    fun goBack() {
        navigationService.goBack()
    }

    open fun dispose() {}

    override fun onCleared() {
        super.onCleared()

        dispose()
    }
}