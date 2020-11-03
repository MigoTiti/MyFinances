package com.lucasrodrigues.myfinances.interactor

import com.lucasrodrigues.myfinances.interactor.base.sync.SyncNoParamNoReturnUseCase
import com.lucasrodrigues.myfinances.repository.AuthRepository
import com.lucasrodrigues.myfinances.repository.FinancialEntryRepository

class Logout(
    private val authRepository: AuthRepository,
    private val financialEntryRepository: FinancialEntryRepository
) : SyncNoParamNoReturnUseCase() {

    override fun run() {
        authRepository.logout()
        financialEntryRepository.reset()
    }
}