package com.lucasrodrigues.myfinances.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Currency(
    val symbol: String,
    val isSymbolInStart: Boolean,
    val formatterPattern: String,
    val decimalSeparator: Char,
    val groupingSeparator: Char
) : Parcelable