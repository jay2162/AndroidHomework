package com.app.androidhomework.di

import com.app.androidhomework.network.APIService
import com.app.androidhomework.viewmodel.ForgotPasswordViewModel
import com.app.androidhomework.viewmodel.LoginViewModel
import com.app.androidhomework.viewmodel.MapViewModel
import com.app.androidhomework.viewmodel.SignUpViewModel
import dagger.Component


@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: APIService)
    fun inject(loginViewModel: LoginViewModel)
    fun inject(signpViewModel: SignUpViewModel)
    fun inject(mapViewModel: MapViewModel)
    fun inject(forgotPasswordViewModel: ForgotPasswordViewModel)

}