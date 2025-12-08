package com.vetalitet.useraccountapp.data.repository

import android.content.Context
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.vetalitet.useraccountapp.data.entities.User
import com.vetalitet.useraccountapp.domain.repository.UserRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : UserRepository {

    private val mapper = jacksonObjectMapper()

    override fun loadUserData(): User {
        val inputStream = context.assets.open("login_response.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        return mapper.readValue(jsonString, User::class.java)
    }

}
