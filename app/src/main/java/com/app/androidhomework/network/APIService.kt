package com.app.androidhomework.network

import com.app.androidhomework.di.DaggerApiComponent
import com.app.androidhomework.models.AuthenticationModel
import com.app.androidhomework.models.MapModel
import com.app.androidhomework.models.CommonModel
import io.reactivex.Observable
import javax.inject.Inject

class APIService {

    @Inject
    lateinit var API: API


    init {
        DaggerApiComponent.create().inject(this)
    }

    fun loginAPI(
        email: String,
        password: String,
        device_token: String,
        device_type: String
    ): Observable<AuthenticationModel> {
        return API.loginAPI(RequestBodies.LoginBody(email, password, device_token, device_type))
    }

    fun signAPI(
        name: String,
        email: String,
        password: String,
    ): Observable<AuthenticationModel> {
        return API.signupAPI(
            RequestBodies.SignUpBody(
                name,
                email,
                password
            )
        )
    }

    fun mapAPI(token: String): Observable<MapModel> {
        return API.mappost(token)
    }

    fun fogotPasswordAPI(email: String): Observable<CommonModel> {
        return API.forgotPassword(RequestBodies.ForGotPasswordBody(email))
    }


}