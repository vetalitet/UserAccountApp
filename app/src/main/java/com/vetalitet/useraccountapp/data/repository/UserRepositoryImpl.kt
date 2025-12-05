package com.vetalitet.useraccountapp.data.repository

import android.content.Context
import com.vetalitet.useraccountapp.data.entities.User
import com.vetalitet.useraccountapp.domain.repository.UserRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : UserRepository {

    override fun loadUserData(): User {
        TODO("Not yet implemented")
    }

}
