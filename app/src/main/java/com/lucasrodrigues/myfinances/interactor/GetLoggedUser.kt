package com.lucasrodrigues.myfinances.interactor

import com.lucasrodrigues.myfinances.interactor.base.sync.SyncNoParamUseCase
import com.lucasrodrigues.myfinances.model.User
import com.lucasrodrigues.myfinances.repository.AuthRepository

class GetLoggedUser(
    private val authRepository: AuthRepository
) : SyncNoParamUseCase<User?>() {
    override fun run(): User? {
        return authRepository.getLoggedUser()
    }
}