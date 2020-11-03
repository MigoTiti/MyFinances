package com.lucasrodrigues.myfinances.view

import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.text.FirstBaseline
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import com.lucasrodrigues.myfinances.extensions.formatAsCurrencyWithoutSymbol
import com.lucasrodrigues.myfinances.utils.Constants
import com.lucasrodrigues.myfinances.view_model.ListEntriesViewModel
import java.text.SimpleDateFormat
import java.util.*

class ListEntriesActivity : BaseActivity<ListEntriesViewModel>() {

    override val parameters: List<Any>
        get() = listOf(intent.getParcelableExtra<Constants.EntryType>("type")!!)

    override fun initialize() {
        setContent {
            ListEntriesView(viewModel)
        }
    }
}

@Composable
fun ListEntriesView(viewModel: ListEntriesViewModel) {
    val type by viewModel.entryType.observeAsState(viewModel.entryType.value!!)

    val date by viewModel.currentDate.observeAsState(Calendar.getInstance())
    val entries by viewModel.entries.observeAsState(listOf())

    val totalSum by viewModel.entrySum.observeAsState(0.0)
    val pendingSum by viewModel.pendingSum.observeAsState(0.0)

    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(if (type == Constants.EntryType.REVENUE) "Receitas" else "Despesas")
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.goBack() }) {
                        Icon(Icons.Filled.ArrowBack)
                    }
                },
            )
        },
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { viewModel.previousMonth() }) {
                    Icon(Icons.Filled.ArrowBack)
                }
                Text(
                    SimpleDateFormat("MMMM/yyyy").format(date.time),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    style = MaterialTheme.typography.h5
                )
                IconButton(onClick = { viewModel.nextMonth() }) {
                    Icon(Icons.Filled.ArrowForward)
                }
            }
            Row(modifier = Modifier.padding(top = 8.dp)) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            "Total",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Row {
                            Text(
                                Constants.DEFAULT_CURRENCY.symbol,
                                modifier = Modifier.alignBy(FirstBaseline),
                                style = MaterialTheme.typography.body1
                            )
                            Text(
                                totalSum.formatAsCurrencyWithoutSymbol(),
                                modifier = Modifier
                                    .alignBy(FirstBaseline)
                                    .padding(start = 8.dp),
                                style = MaterialTheme.typography.h5
                            )
                        }
                    }
                }
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Pendente")
                        Row {
                            Text(
                                Constants.DEFAULT_CURRENCY.symbol,
                                modifier = Modifier.alignBy(FirstBaseline),
                                style = MaterialTheme.typography.body1
                            )
                            Text(
                                pendingSum.formatAsCurrencyWithoutSymbol(),
                                modifier = Modifier
                                    .alignBy(FirstBaseline)
                                    .padding(start = 8.dp),
                                style = MaterialTheme.typography.h5
                            )
                        }
                    }
                }
            }
            LazyColumnFor(
                modifier = Modifier.fillMaxSize(),
                items = entries
            ) { entry ->
                Row(
                    modifier = Modifier
                        .clickable(onClick = { viewModel.editEntry(entry) })
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Row {
                            Text(
                                Constants.DEFAULT_CURRENCY.symbol,
                                modifier = Modifier.alignBy(FirstBaseline),
                                style = MaterialTheme.typography.body1
                            )
                            Text(
                                entry.value.formatAsCurrencyWithoutSymbol(),
                                modifier = Modifier
                                    .alignBy(FirstBaseline)
                                    .padding(start = 8.dp),
                                style = MaterialTheme.typography.body1
                            )
                        }
                        Text(
                            SimpleDateFormat("dd/MMM/yyyy").format(entry.date),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
                if (entry != entries.last())
                    Divider()
            }
        }
    }
}
