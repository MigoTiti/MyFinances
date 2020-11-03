package com.lucasrodrigues.myfinances.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

abstract class FinancialEntry(
    @Transient open val id: String?,
    @Transient open val description: String,
    @Transient open val value: Double,
    @Transient open val date: Date,
    @Transient open val consummate: Boolean
) : Parcelable {

    @Parcelize
    data class Expense(
        override val id: String? = null,
        override val description: String = "",
        override val value: Double = 0.0,
        override val date: Date = Date(),
        override val consummate: Boolean = false
    ) : FinancialEntry(id, description, value, date, consummate)

    @Parcelize
    data class Revenue(
        override val id: String? = null,
        override val description: String = "",
        override val value: Double = 0.0,
        override val date: Date = Date(),
        override val consummate: Boolean = false
    ) : FinancialEntry(id, description, value, date, consummate)
}