package com.lucasrodrigues.myfinances.interactor

import com.lucasrodrigues.myfinances.interactor.base.stream.StreamNoParamUseCase
import com.lucasrodrigues.myfinances.repository.FinancialEntryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class ObserveUserBalance(
    private val financialEntryRepository: FinancialEntryRepository
) : StreamNoParamUseCase<Double>() {
    @ExperimentalCoroutinesApi
    override fun run(): Flow<Double> {
        return financialEntryRepository.observeUserBalance()
    }
}