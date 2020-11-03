package com.lucasrodrigues.myfinances.interactor

import com.lucasrodrigues.myfinances.interactor.base.sync.SyncNoParamUseCase
import com.lucasrodrigues.myfinances.repository.AuthRepository

class HasLoggedUser(
    private val authRepository: AuthRepository
) : SyncNoParamUseCase<Boolean>() {
    override fun run(): Boolean {
        return authRepository.hasLoggedUser()
    }
}