package com.lucasrodrigues.myfinances.interactor.base.sync

abstract class SyncNoReturnUseCase<in Params> {
    abstract fun run(params: Params)

    operator fun invoke(params: Params) {
        run(params)
    }
}