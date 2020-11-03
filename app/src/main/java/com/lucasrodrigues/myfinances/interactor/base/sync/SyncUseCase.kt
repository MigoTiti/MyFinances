package com.lucasrodrigues.myfinances.interactor.base.sync

abstract class SyncUseCase<in Params, out Return> {
    abstract fun run(params: Params): Return

    operator fun invoke(params: Params): Return {
        return run(params)
    }
}