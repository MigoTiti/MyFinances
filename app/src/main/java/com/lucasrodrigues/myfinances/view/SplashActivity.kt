package com.lucasrodrigues.myfinances.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.ui.tooling.preview.Preview
import com.lucasrodrigues.myfinances.widgets.Center
import com.lucasrodrigues.myfinances.view_model.SplashViewModel

class SplashActivity : BaseActivity<SplashViewModel>() {
    override fun initialize() {
        setContent {
            SplashView()
        }

        viewModel.checkUserSession()
    }
}

@Preview(showBackground = true)
@Composable
fun SplashView() {
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
    ) {
        Center(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
    }
}
