package com.lucasrodrigues.myfinances.interactor

import com.lucasrodrigues.myfinances.interactor.base.async.AsyncNoReturnUseCase
import com.lucasrodrigues.myfinances.repository.AuthRepository

class Register(
    private val authRepository: AuthRepository
) : AsyncNoReturnUseCase<Register.Params>() {

    override suspend fun run(params: Params) {
        authRepository.register(params.email, params.password, params.name)
    }

    data class Params(
        val email: String,
        val password: String,
        val name: String
    )
}