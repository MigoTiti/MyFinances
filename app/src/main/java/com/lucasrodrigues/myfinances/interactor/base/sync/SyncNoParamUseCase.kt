package com.lucasrodrigues.myfinances.interactor.base.sync

abstract class SyncNoParamUseCase<out Return> {
    abstract fun run(): Return

    operator fun invoke(): Return {
        return run()
    }
}