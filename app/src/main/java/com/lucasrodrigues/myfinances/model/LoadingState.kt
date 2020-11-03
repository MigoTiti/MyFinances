package com.lucasrodrigues.myfinances.model

abstract class LoadingState {
    object Active : LoadingState()

    object Idle : LoadingState()

    class Error(val exception: Exception) : LoadingState()
}