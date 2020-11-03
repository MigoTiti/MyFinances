package com.lucasrodrigues.myfinances.interactor.base.async

abstract class AsyncNoParamNoReturnUseCase {
    abstract suspend fun run()

    suspend operator fun invoke() {
        run()
    }
}