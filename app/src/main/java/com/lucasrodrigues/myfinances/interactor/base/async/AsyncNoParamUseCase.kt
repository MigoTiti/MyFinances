package com.lucasrodrigues.myfinances.interactor.base.async

abstract class AsyncNoParamUseCase<out Return> {
    abstract suspend fun run(): Return

    suspend operator fun invoke(): Return {
        return run()
    }
}