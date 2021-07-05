package com.app.androidhomework.models

data class AuthenticationModel(
    val authentication_token: String,
    val person: Person
)

data class Person(
    val app_info_keys: List<Any>,
    val display_name: String,
    val email: String,
    val key: String,
    val role: Role
)

data class Role(
    val key: String,
    val rank: Int
)