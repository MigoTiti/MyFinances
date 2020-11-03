package com.lucasrodrigues.myfinances.view

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.lucasrodrigues.myfinances.model.FinancialEntry
import com.lucasrodrigues.myfinances.view_model.ModifyEntryViewModel
import java.text.SimpleDateFormat
import java.util.*

class ModifyEntryActivity : BaseActivity<ModifyEntryViewModel>() {
    override val parameters: List<Any>
        get() = listOf(intent.getParcelableExtra<FinancialEntry>("initialEntry")!!)

    override fun initialize() {
        setContent {
            ModifyEntryView(viewModel)
        }
    }
}

@Composable
fun ModifyEntryView(viewModel: ModifyEntryViewModel) {
    val isEdit by viewModel.exists.observeAsState(viewModel.exists.value ?: false)
    val isExpense by viewModel.isExpense.observeAsState(viewModel.isExpense.value ?: false)

    val description by viewModel.description.observeAsState("")
    val date by viewModel.date.observeAsState()
    val value by viewModel.value.observeAsState("")
    val consummate by viewModel.consummate.observeAsState(false)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "${
                            if (isEdit)
                                "Editar"
                            else
                                "Adicionar"
                        } ${
                            if (isExpense)
                                "Despesa"
                            else
                                "Receita"
                        }"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.goBack() }) {
                        Icon(Icons.Filled.ArrowBack)
                    }
                },
                actions = {
                    if (isEdit)
                        IconButton(onClick = { viewModel.delete() }) {
                            Icon(Icons.Filled.Delete)
                        }
                    IconButton(onClick = { viewModel.tryConfirm() }) {
                        Icon(Icons.Filled.Check)
                    }
                }
            )
        },
        backgroundColor = MaterialTheme.colors.background,
    ) {
        ScrollableColumn {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 32.dp,
                        vertical = 16.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    value = value,
                    keyboardType = KeyboardType.Number,
                    onValueChange = { viewModel.value.value = it },
                    label = {
                        Text("Valor")
                    }
                )
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                        .clickable(onClick = { viewModel.pickDate() })
                ) {
                    Text("Data")
                    Text(
                        SimpleDateFormat("dd/MM/yyyy").format(date ?: Date()),
                        style = MaterialTheme.typography.h6
                    )
                }
                TextField(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    value = description,
                    keyboardType = KeyboardType.Text,
                    onValueChange = { viewModel.description.value = it },
                    label = {
                        Text("Descrição")
                    }
                )
                Row(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.Start)
                ) {
                    Switch(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        checked = consummate,
                        onCheckedChange = { viewModel.consummate.postValue(it) }
                    )
                    Text(
                        if (isExpense) "Pago" else "Recebido",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 8.dp)
                    )
                }
            }
        }
    }
}
