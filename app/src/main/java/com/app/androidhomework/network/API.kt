package com.app.androidhomework.network

import com.app.androidhomework.models.AuthenticationModel
import com.app.androidhomework.models.CommonModel
import com.app.androidhomework.models.MapModel
import io.reactivex.Observable
import retrofit2.http.*

interface API {

    @Headers("Accept: application/json")
    @POST("api/v2/people/authenticate")
    fun loginAPI(@Body body: RequestBodies.LoginBody): Observable<AuthenticationModel>

    @Headers("Content-Type: application/json")
    @POST("api/v2/people/create")
    fun signupAPI(@Body body: RequestBodies.SignUpBody): Observable<AuthenticationModel>

    @Headers("Content-Type: application/json")
    @GET("api/v2/vehicles")
    fun mappost(@Header("Authorization") auth: String): Observable<MapModel>

    @Headers("Content-Type: application/json")
    @POST("api/v2/people/reset_password")
    fun forgotPassword(@Body body: RequestBodies.ForGotPasswordBody): Observable<CommonModel>

}

