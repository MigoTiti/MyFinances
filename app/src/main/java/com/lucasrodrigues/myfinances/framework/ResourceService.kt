package com.lucasrodrigues.myfinances.framework

import android.content.Context
import androidx.core.content.ContextCompat

class ResourceService(
    private val context: Context
) {

    fun getString(id: Int, vararg args: Any): String {
        return context.getString(id, *args)
    }

    fun getColor(id: Int): Int {
        return ContextCompat.getColor(context, id)
    }

    fun getErrorMessage(exception: Throwable): String {
        return when (exception) {
            else -> exception.localizedMessage
                ?: exception.message
                ?: ""
        }
    }
}