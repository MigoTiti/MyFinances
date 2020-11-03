package com.lucasrodrigues.myfinances.interactor.base.async

abstract class AsyncNoReturnUseCase<in Params> {
    abstract suspend fun run(params: Params)

    suspend operator fun invoke(params: Params) {
        run(params)
    }
}