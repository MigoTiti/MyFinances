package com.lucasrodrigues.myfinances.framework

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DialogService(
    activity: AppCompatActivity
) {
    private val activity = WeakReference(activity)

    suspend fun pickDate(initialDate: Date): Date? {
        return suspendCoroutine { continuation ->
            val activity = activity.get()

            if (activity != null) {
                activity.runOnUiThread {
                    val dialog = datePicker().apply {
                        setSelection(initialDate.time)
                    }.build()

                    dialog.addOnCancelListener {
                        continuation.resume(null)
                    }

                    dialog.addOnNegativeButtonClickListener {
                        continuation.resume(null)
                    }

                    dialog.addOnPositiveButtonClickListener {
                        val utcTime = Date(it)
                        val format = "yyyy/MM/dd HH:mm:ss"
                        val sdf = SimpleDateFormat(format, Locale.getDefault()).apply {
                            TimeZone.getTimeZone("UTC")
                        }

                        continuation.resume(
                            SimpleDateFormat(
                                format,
                                Locale.getDefault()
                            ).parse(sdf.format(utcTime))
                        )
                    }

                    dialog.show(activity.supportFragmentManager, dialog.toString())
                }
            } else {
                continuation.resume(null)
            }
        }
    }
}