package com.lucasrodrigues.myfinances.interactor

import android.os.Parcelable
import com.lucasrodrigues.myfinances.interactor.base.async.AsyncNoReturnUseCase
import com.lucasrodrigues.myfinances.model.FinancialEntry
import com.lucasrodrigues.myfinances.repository.FinancialEntryRepository
import kotlinx.android.parcel.Parcelize

class UpdateEntry(
    private val financialEntryRepository: FinancialEntryRepository
) : AsyncNoReturnUseCase<UpdateEntry.Params>() {
    override suspend fun run(params: Params) {
        financialEntryRepository.updateEntry(params.entry)
    }

    @Parcelize
    data class Params(
        val entry: FinancialEntry
    ) : Parcelable
}