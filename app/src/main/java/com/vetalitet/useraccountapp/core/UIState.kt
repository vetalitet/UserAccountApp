package com.vetalitet.useraccountapp.core

sealed class UiState<out T : Any> {
    object Loading : UiState<Nothing>()
    data class Error(val message: String? = null) : UiState<Nothing>()
    data class Success<out T : Any>(val result: T? = null) : UiState<T>()
}
