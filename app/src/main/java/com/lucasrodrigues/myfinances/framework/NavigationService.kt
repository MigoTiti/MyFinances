package com.lucasrodrigues.myfinances.framework

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import com.lucasrodrigues.myfinances.model.FinancialEntry
import com.lucasrodrigues.myfinances.utils.Constants
import com.lucasrodrigues.myfinances.view.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.newTask
import org.jetbrains.anko.singleTop
import java.io.Serializable
import java.lang.ref.WeakReference

class NavigationService(
    activity: Activity
) {
    private val activity = WeakReference(activity)

    fun goToListExpenses() {
        startActivity(
            ListEntriesActivity::class.java,
            params = arrayOf("type" to Constants.EntryType.EXPENSE)
        )
    }

    fun goToListRevenues() {
        startActivity(
            ListEntriesActivity::class.java,
            params = arrayOf("type" to Constants.EntryType.REVENUE)
        )
    }

    fun goToAddExpense() {
        startActivity(
            ModifyEntryActivity::class.java,
            params = arrayOf("initialEntry" to FinancialEntry.Expense())
        )
    }

    fun goToAddRevenue() {
        startActivity(
            ModifyEntryActivity::class.java,
            params = arrayOf("initialEntry" to FinancialEntry.Revenue())
        )
    }

    fun goToEditEntry(entry: FinancialEntry) {
        startActivity(
            ModifyEntryActivity::class.java,
            params = arrayOf("initialEntry" to entry)
        )
    }

    fun goToLogin() {
        startActivity(
            LoginActivity::class.java,
            clearNavigationStack = true
        )
    }

    fun goToRegister() {
        startActivity(RegisterActivity::class.java)
    }

    fun goToMain() {
        startActivity(
            MainActivity::class.java,
            clearNavigationStack = true
        )
    }

    fun goBack() {
        activity.get()?.let {
            with(it) {
                runOnUiThread {
                    finish()
                }
            }
        }
    }

    private fun buildIntent(
        activityClass: Class<*>? = null,
        context: Context? = null,
        vararg params: Pair<String, Any?>
    ): Intent {
        return (if (activityClass != null) {
            Intent(context, activityClass)
        } else {
            Intent()
        }).apply {
            params.forEach { pair ->
                when (val value = pair.second) {
                    null -> putExtra(pair.first, null as Serializable?)
                    is Int -> putExtra(pair.first, value)
                    is Long -> putExtra(pair.first, value)
                    is CharSequence -> putExtra(pair.first, value)
                    is String -> putExtra(pair.first, value)
                    is Float -> putExtra(pair.first, value)
                    is Double -> putExtra(pair.first, value)
                    is Char -> putExtra(pair.first, value)
                    is Short -> putExtra(pair.first, value)
                    is Boolean -> putExtra(pair.first, value)
                    is Serializable -> putExtra(pair.first, value)
                    is Bundle -> putExtra(pair.first, value)
                    is Parcelable -> putExtra(pair.first, value)
                    is Array<*> -> when {
                        value.isArrayOf<CharSequence>() -> putExtra(pair.first, value)
                        value.isArrayOf<String>() -> putExtra(pair.first, value)
                        value.isArrayOf<Parcelable>() -> putExtra(pair.first, value)
                    }
                    is IntArray -> putExtra(pair.first, value)
                    is LongArray -> putExtra(pair.first, value)
                    is FloatArray -> putExtra(pair.first, value)
                    is DoubleArray -> putExtra(pair.first, value)
                    is CharArray -> putExtra(pair.first, value)
                    is ShortArray -> putExtra(pair.first, value)
                    is BooleanArray -> putExtra(pair.first, value)
                }
            }
        }
    }

    private fun startActivity(
        activityClass: Class<*>,
        clearNavigationStack: Boolean = false,
        clearTop: Boolean = true,
        singleTop: Boolean = true,
        newTask: Boolean = false,
        requestCode: Int? = null,
        vararg params: Pair<String, Any?>,
        options: Bundle? = null,
        noAnimation: Boolean = false
    ) {
        activity.get()?.let {
            with(it) {
                runOnUiThread {
                    val intent = buildIntent(
                        activityClass = activityClass,
                        context = this,
                        params = params
                    ).apply {
                        if (singleTop)
                            singleTop()

                        if (clearTop)
                            clearTop()

                        if (newTask)
                            newTask()
                    }

                    if (requestCode == null)
                        startActivity(intent, options)
                    else
                        startActivityForResult(intent, requestCode, options)

                    if (clearNavigationStack)
                        finishAffinity()

                    if (noAnimation)
                        overridePendingTransition(0, 0)
                }
            }
        }
    }
}