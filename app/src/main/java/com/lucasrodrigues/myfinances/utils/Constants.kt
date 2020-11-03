package com.lucasrodrigues.myfinances.utils

import android.os.Parcelable
import com.lucasrodrigues.myfinances.model.Currency
import kotlinx.android.parcel.Parcelize

object Constants {
    val DEFAULT_CURRENCY = Currency(
        formatterPattern = "###,###,##0.00",
        isSymbolInStart = true,
        symbol = "R$",
        decimalSeparator = ',',
        groupingSeparator = '.'
    )

    @Parcelize
    enum class EntryType : Parcelable {
        EXPENSE,
        REVENUE
    }
}