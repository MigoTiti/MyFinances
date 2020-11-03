package com.lucasrodrigues.myfinances.interactor

import android.os.Parcelable
import com.lucasrodrigues.myfinances.interactor.base.async.AsyncNoReturnUseCase
import com.lucasrodrigues.myfinances.model.FinancialEntry
import com.lucasrodrigues.myfinances.repository.FinancialEntryRepository
import kotlinx.android.parcel.Parcelize

class CreateEntry(
    private val financialEntryRepository: FinancialEntryRepository
) : AsyncNoReturnUseCase<CreateEntry.Params>() {
    override suspend fun run(params: Params) {
        financialEntryRepository.createEntry(params.entry)
    }

    @Parcelize
    data class Params(
        val entry: FinancialEntry
    ) : Parcelable
}