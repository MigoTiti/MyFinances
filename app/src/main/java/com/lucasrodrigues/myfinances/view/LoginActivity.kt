package com.lucasrodrigues.myfinances.view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ClickableText
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.lucasrodrigues.myfinances.widgets.Center
import com.lucasrodrigues.myfinances.model.LoadingState
import com.lucasrodrigues.myfinances.view_model.LoginViewModel

class LoginActivity : BaseActivity<LoginViewModel>() {
    override fun initialize() {
        setContent {
            LoginView(viewModel)
        }
    }
}

@Composable
fun LoginView(viewModel: LoginViewModel) {
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")

    val loggingIn by viewModel.loggingIn.observeAsState(LoadingState.Idle)

    Scaffold(
        backgroundColor = MaterialTheme.colors.primary,
    ) {
        Center(modifier = Modifier.fillMaxSize()) {
            Card(backgroundColor = MaterialTheme.colors.onPrimary) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Entrar",
                        modifier = Modifier
                            .padding(bottom = 24.dp)
                            .align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.h6
                    )
                    TextField(
                        value = email,
                        imeAction = ImeAction.Next,
                        onValueChange = { viewModel.email.value = it },
                        label = {
                            Text("Email")
                        }
                    )
                    TextField(
                        modifier = Modifier.padding(top = 16.dp),
                        value = password,
                        onValueChange = { viewModel.password.value = it },
                        visualTransformation = PasswordVisualTransformation(),
                        label = {
                            Text("Senha")
                        }
                    )
                    Button(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .align(Alignment.CenterHorizontally)
                            .animateContentSize(),
                        onClick = { viewModel.tryLogin() },
                        enabled = loggingIn !is LoadingState.Active
                    ) {
                        if (loggingIn is LoadingState.Active) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .height(32.dp)
                                    .width(32.dp),
                                strokeWidth = 4.dp
                            )
                        } else {
                            Text("Login")
                        }
                    }
                    ClickableText(
                        modifier = Modifier.padding(top = 16.dp),
                        text = AnnotatedString
                            .Builder("Não possui uma conta? Cadastre-se")
                            .apply {
                                addStyle(
                                    SpanStyle(
                                        color = MaterialTheme.colors.primary,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    22,
                                    33
                                )
                            }.toAnnotatedString(),
                        onClick = { viewModel.register() }
                    )
                }
            }
        }
    }
}
