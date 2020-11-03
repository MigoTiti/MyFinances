package com.lucasrodrigues.myfinances.interactor

import com.lucasrodrigues.myfinances.interactor.base.stream.StreamUseCase
import com.lucasrodrigues.myfinances.model.FinancialEntry
import com.lucasrodrigues.myfinances.repository.FinancialEntryRepository
import kotlinx.coroutines.flow.Flow

class ObserveRevenues(
    private val financialEntryRepository: FinancialEntryRepository
) : StreamUseCase<ObserveRevenues.Params, List<FinancialEntry.Revenue>>() {

    override fun run(params: Params): Flow<List<FinancialEntry.Revenue>> {
        return financialEntryRepository.observeRevenues(params.month, params.year)
    }

    data class Params(
        val month: Int,
        val year: Int
    )
}