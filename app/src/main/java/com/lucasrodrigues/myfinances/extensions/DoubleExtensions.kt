package com.lucasrodrigues.myfinances.extensions

import com.lucasrodrigues.myfinances.model.Currency
import com.lucasrodrigues.myfinances.utils.Constants.DEFAULT_CURRENCY
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

fun Double.formatAsCurrencyWithoutSymbol(currency: Currency = DEFAULT_CURRENCY): String {
    val df2 = DecimalFormat(
        currency.formatterPattern,
        DecimalFormatSymbols().apply {
            this.groupingSeparator = currency.groupingSeparator
            this.decimalSeparator = currency.decimalSeparator
        }
    )

    return df2.format(this)
}