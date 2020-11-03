package com.lucasrodrigues.myfinances.view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.lucasrodrigues.myfinances.model.LoadingState
import com.lucasrodrigues.myfinances.view_model.RegisterViewModel

class RegisterActivity : BaseActivity<RegisterViewModel>() {
    override fun initialize() {
        setContent {
            RegisterView(viewModel)
        }
    }
}

@Composable
fun RegisterView(viewModel: RegisterViewModel) {
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")
    val passwordConfirmation by viewModel.passwordConfirmation.observeAsState("")
    val name by viewModel.name.observeAsState("")

    val registering by viewModel.registering.observeAsState(LoadingState.Idle)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Cadastro")
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.goBack() }) {
                        Icon(Icons.Filled.ArrowBack)
                    }
                },
            )
        },
        backgroundColor = MaterialTheme.colors.background,
    ) {
        ScrollableColumn(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 16.dp
                )
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                imeAction = ImeAction.Next,
                onValueChange = { viewModel.name.value = it },
                keyboardType = KeyboardType.Ascii,
                label = {
                    Text("Nome")
                }
            )
            TextField(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                value = email,
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email,
                onValueChange = { viewModel.email.value = it },
                label = {
                    Text("Email")
                }
            )
            TextField(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .fillMaxWidth(),
                value = password,
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { viewModel.password.value = it },
                label = {
                    Text("Senha")
                }
            )
            TextField(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                value = passwordConfirmation,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password,
                onImeActionPerformed = { _, _ ->
                    viewModel.tryRegister()
                },
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { viewModel.passwordConfirmation.value = it },
                label = {
                    Text("Confirme sua senha")
                }
            )
            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .animateContentSize(),
                onClick = { viewModel.tryRegister() },
                enabled = registering !is LoadingState.Active
            ) {
                if (registering is LoadingState.Active) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .height(32.dp)
                            .width(32.dp),
                        strokeWidth = 4.dp
                    )
                } else {
                    Text("Confirmar")
                }
            }
        }
    }
}
