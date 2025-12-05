package com.vetalitet.useraccountapp.domain.repository

import com.vetalitet.useraccountapp.data.entities.User

interface UserRepository {
    fun loadUserData(): User
}
