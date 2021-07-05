package com.app.androidhomework.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.androidhomework.di.DaggerApiComponent
import com.app.androidhomework.models.AuthenticationModel
import com.app.androidhomework.network.APIService
import com.app.androidhomework.nw.ObserverUtil.subscribeToSingle
import com.app.androidhomework.nw.WebserviceBuilder
import com.app.dripz.nw.SingleCallback
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class LoginViewModel : ViewModel(), SingleCallback {

    val loginData = MutableLiveData<AuthenticationModel>()
    val loginError = MutableLiveData<Boolean>()
    val loadingProgressBar = MutableLiveData<Boolean>()
    val fcmToken = MutableLiveData<String>()
    val apiError = MutableLiveData<String>()
    private var compositeDisposable: CompositeDisposable? = null

    @Inject
    lateinit var apiservice: APIService
    val disposable = CompositeDisposable()

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun loginAPI(email: String, password: String, device_token: String, device_type: String) {
        loadingProgressBar.value = true
        subscribeToSingle(
            apiservice.loginAPI(email, password, device_token, device_type),
            getCompositeDisposable(), WebserviceBuilder.ApiNames.Login, this
        )
    }

    override fun onSingleSuccess(o: Any?, apiNames: WebserviceBuilder.ApiNames?) {
        when (apiNames) {
            WebserviceBuilder.ApiNames.Login -> {
                loadingProgressBar.value = false
                loginError.value = false
                loginData.value = o as AuthenticationModel
            }
            else -> {

            }
        }
    }

    override fun onFailure(
        throwable: Throwable?,
        apiNames: WebserviceBuilder.ApiNames?,
        messages: String
    ) {
        loadingProgressBar.value = false
        apiError.value = messages
        loginError.value = true
    }

    fun getCompositeDisposable(): CompositeDisposable? {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
        if (compositeDisposable!!.isDisposed) compositeDisposable = CompositeDisposable()
        return compositeDisposable
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}