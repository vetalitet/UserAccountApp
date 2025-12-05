package com.vetalitet.useraccountapp.data.entities

import com.fasterxml.jackson.annotation.JsonProperty

data class User(
    @JsonProperty("photo_header")
    val photoHeader: String,
    val avatar: String,
    @JsonProperty("first_name")
    val firstName: String,
    @JsonProperty("last_name")
    val lastName: String,
    val about: String,
    val age: Long,
    val login: String,
    val password: String,
)
