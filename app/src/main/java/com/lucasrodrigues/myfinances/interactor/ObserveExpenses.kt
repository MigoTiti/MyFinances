package com.lucasrodrigues.myfinances.interactor

import com.lucasrodrigues.myfinances.interactor.base.stream.StreamUseCase
import com.lucasrodrigues.myfinances.model.FinancialEntry
import com.lucasrodrigues.myfinances.repository.FinancialEntryRepository
import kotlinx.coroutines.flow.Flow

class ObserveExpenses(
    private val financialEntryRepository: FinancialEntryRepository
) : StreamUseCase<ObserveExpenses.Params, List<FinancialEntry.Expense>>() {

    override fun run(params: Params): Flow<List<FinancialEntry.Expense>> {
        return financialEntryRepository.observeExpenses(params.month, params.year)
    }

    data class Params(
        val month: Int,
        val year: Int
    )
}