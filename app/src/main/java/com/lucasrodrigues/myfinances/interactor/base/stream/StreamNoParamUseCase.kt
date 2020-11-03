package com.lucasrodrigues.myfinances.interactor.base.stream

import kotlinx.coroutines.flow.Flow

abstract class StreamNoParamUseCase<out Return> {
    abstract fun run(): Flow<Return>

    operator fun invoke(): Flow<Return> {
        return run()
    }
}