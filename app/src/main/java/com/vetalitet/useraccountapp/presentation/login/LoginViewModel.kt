package com.vetalitet.useraccountapp.presentation.login

import androidx.lifecycle.ViewModel
import com.vetalitet.useraccountapp.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
}
