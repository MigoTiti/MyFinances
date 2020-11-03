package com.lucasrodrigues.myfinances.view

import androidx.compose.foundation.ClickableText
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.FirstBaseline
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.lucasrodrigues.myfinances.extensions.formatAsCurrencyWithoutSymbol
import com.lucasrodrigues.myfinances.utils.Constants.DEFAULT_CURRENCY
import com.lucasrodrigues.myfinances.view_model.MainViewModel

class MainActivity : BaseActivity<MainViewModel>() {
    override fun initialize() {
        setContent {
            MainView(viewModel)
        }
    }
}

@Composable
fun MainView(viewModel: MainViewModel) {
    val user by viewModel.loggedUser.observeAsState()
    val userBalance by viewModel.userBalance.observeAsState(0.0)

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text("MyFinances")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (scaffoldState.drawerState.isOpen)
                                scaffoldState.drawerState.close()
                            else
                                scaffoldState.drawerState.open()
                        }
                    ) {
                        Icon(Icons.Filled.Menu)
                    }
                },
            )
        },
        drawerContent = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = 32.dp,
                        vertical = 16.dp
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        user?.name ?: "",
                        style = MaterialTheme.typography.h4
                    )
                    Text(
                        user?.email ?: "",
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                Divider(modifier = Modifier.padding(vertical = 16.dp), thickness = 2.dp)
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    ClickableText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 32.dp,
                                vertical = 8.dp
                            ),
                        text = AnnotatedString("Sair"),
                        onClick = { viewModel.tryLogout() }
                    )
                }
            }
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
                Text("Saldo atual")
                Row {
                    Text(
                        DEFAULT_CURRENCY.symbol,
                        modifier = Modifier.alignBy(FirstBaseline),
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        userBalance.formatAsCurrencyWithoutSymbol(),
                        modifier = Modifier
                            .alignBy(FirstBaseline)
                            .padding(start = 8.dp),
                        style = MaterialTheme.typography.h3
                    )
                }
                Row(modifier = Modifier.padding(top = 32.dp)) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "Receitas",
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                onClick = { viewModel.listRevenues() }
                            ) {
                                Text("Listar")
                            }
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                onClick = { viewModel.addRevenue() }
                            ) {
                                Text("Adicionar")
                            }
                        }
                    }
                    Card(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "Despesas",
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                onClick = { viewModel.listExpenses() }
                            ) {
                                Text("Listar")
                            }
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                onClick = { viewModel.addExpense() }
                            ) {
                                Text("Adicionar")
                            }
                        }
                    }
                }
            }
        }
    }
}
