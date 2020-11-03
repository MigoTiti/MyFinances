package com.lucasrodrigues.myfinances.interactor

import com.lucasrodrigues.myfinances.interactor.base.async.AsyncNoReturnUseCase
import com.lucasrodrigues.myfinances.repository.AuthRepository

class Login(
    private val authRepository: AuthRepository
) : AsyncNoReturnUseCase<Login.Params>() {

    override suspend fun run(params: Params) {
        authRepository.login(params.email, params.password)
    }

    data class Params(
        val email: String,
        val password: String
    )
}