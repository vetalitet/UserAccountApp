package com.vetalitet.useraccountapp.presentation.login

import androidx.lifecycle.viewModelScope
import com.vetalitet.useraccountapp.core.UiState
import com.vetalitet.useraccountapp.core.base.BaseViewModel
import com.vetalitet.useraccountapp.data.entities.User
import com.vetalitet.useraccountapp.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _userFlow = MutableStateFlow<UiState<User>>(UiState.Success(null))
    val userFlow: Flow<UiState<User>> = _userFlow

    private val _loginFlow = MutableSharedFlow<UiState<Boolean>>()
    val loginFlow: Flow<UiState<Boolean>> = _loginFlow

    fun loadUser() {
        viewModelScope.launch {
            try {
                val user = userRepository.loadUserData()
                _userFlow.emit(UiState.Success(user))
            } catch (e: Exception) {
                _userFlow.emit(UiState.Error(e.message ?: "Unknown Error"))
            }
        }
    }

    fun login(login: String, password: String) {
        viewModelScope.launch {
            val currentState = _userFlow.value
            if (currentState is UiState.Success) {
                val user = currentState.result
                if (user != null && user.login == login && user.password == password) {
                    _loginFlow.emit(UiState.Success(true))
                } else {
                    _loginFlow.emit(UiState.Success(false))
                }
            } else {
                _loginFlow.emit(UiState.Error("User data not loaded"))
            }
        }
    }

}
