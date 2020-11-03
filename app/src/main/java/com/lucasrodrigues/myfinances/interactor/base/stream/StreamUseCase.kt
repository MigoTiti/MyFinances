package com.lucasrodrigues.myfinances.interactor.base.stream

import kotlinx.coroutines.flow.Flow

abstract class StreamUseCase<in Params, out Return> {
    abstract fun run(params: Params): Flow<Return>

    operator fun invoke(params: Params): Flow<Return> {
        return run(params)
    }
}