package com.vetalitet.useraccountapp.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vetalitet.useraccountapp.core.UiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected open fun <T : Any> launchWithUIState(
        block: suspend CoroutineScope.() -> Result<T>,
        flow: MutableStateFlow<UiState<T>>
    ): Job {
        return launchCoroutine(
            block,
            onStart = { flow.value = UiState.Loading },
            onError = { flow.value = UiState.Error(it?.message) },
            onSuccess = { flow.value = UiState.Success(it) }
        )
    }

    protected open fun <T, R : Any> launchWithUIState(
        block: suspend CoroutineScope.() -> Result<T>,
        flow: MutableStateFlow<UiState<R>>,
        transform: (T) -> R
    ): Job {
        return launchCoroutine(
            block,
            onStart = { flow.value = UiState.Loading },
            onError = { flow.value = UiState.Error(it?.message) },
            onSuccess = { flow.value = UiState.Success(transform(it)) }
        )
    }

    protected open fun <T> launchCoroutine(
        block: suspend CoroutineScope.() -> Result<T>,
        onSuccess: (T) -> Unit,
        onError: (Throwable?) -> Unit = ::handleError,
        onStart: () -> Unit = {},
        onEnd: () -> Unit = {}
    ) = launch(onError) {
        onStart()
        val result = block()
        when {
            result.isSuccess -> result.getOrNull()?.let(onSuccess)
            result.isFailure -> onError(result.exceptionOrNull())
        }
        onEnd()
    }

    protected open fun launch(
        onError: (Throwable) -> Unit,
        block: suspend CoroutineScope.() -> Unit
    ): Job =
        viewModelScope.launch(CoroutineExceptionHandler { _, exception -> onError(exception) }) {
            block()
        }

    protected open fun handleError(throwable: Throwable?) {
        logError(throwable)
    }

    protected open fun logError(throwable: Throwable?) {
        /*if (BuildConfig.DEBUG) {
            logE("Error ---> ${throwable?.message}")
        }*/
    }
}
