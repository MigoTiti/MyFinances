package com.lucasrodrigues.myfinances.interactor.base.async

abstract class AsyncUseCase<in Params, out Return> {
    abstract suspend fun run(params: Params): Return

    suspend operator fun invoke(params: Params): Return {
        return run(params)
    }
}