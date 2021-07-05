package com.app.androidhomework.network

object RequestBodies {

    data class LoginBody(
        val email: String,
        val password: String,
        val device_token: String,
        val device_type: String
    )

    data class SignUpBody(
        val display_name: String,
        val email: String,
        val password: String,
    )

    data class ForGotPasswordBody(
        val email: String

    )

}