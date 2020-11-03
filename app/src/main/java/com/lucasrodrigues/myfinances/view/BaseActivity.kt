package com.lucasrodrigues.myfinances.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.lucasrodrigues.myfinances.framework.AlertService
import com.lucasrodrigues.myfinances.framework.NavigationService
import com.lucasrodrigues.myfinances.framework.ResourceService
import com.lucasrodrigues.myfinances.utils.genericTypeClass
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

abstract class BaseActivity<VM : ViewModel> : AppCompatActivity() {

    val alertService by inject<AlertService> { parametersOf(this) }
    val resourceService by inject<ResourceService> { parametersOf(this) }
    val navigationService by inject<NavigationService> { parametersOf(this) }

    val viewModel: VM by viewModel(genericTypeClass(0)) {
        parametersOf(
            this,
            *parameters.toTypedArray()
        )
    }

    open val parameters
        get() = listOf<Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
    }

    abstract fun initialize()
}