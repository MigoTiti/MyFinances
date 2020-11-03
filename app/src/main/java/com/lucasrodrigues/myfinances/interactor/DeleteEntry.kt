package com.lucasrodrigues.myfinances.interactor

import android.os.Parcelable
import com.lucasrodrigues.myfinances.interactor.base.async.AsyncNoReturnUseCase
import com.lucasrodrigues.myfinances.model.FinancialEntry
import com.lucasrodrigues.myfinances.repository.FinancialEntryRepository
import kotlinx.android.parcel.Parcelize

class DeleteEntry(
    private val financialEntryRepository: FinancialEntryRepository
) : AsyncNoReturnUseCase<DeleteEntry.Params>() {
    override suspend fun run(params: Params) {
        financialEntryRepository.deleteEntry(params.entry)
    }

    @Parcelize
    data class Params(
        val entry: FinancialEntry
    ) : Parcelable
}