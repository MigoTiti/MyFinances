package com.lucasrodrigues.myfinances.interactor.base.sync

abstract class SyncNoParamNoReturnUseCase {
    abstract fun run()

    operator fun invoke() {
        run()
    }
}