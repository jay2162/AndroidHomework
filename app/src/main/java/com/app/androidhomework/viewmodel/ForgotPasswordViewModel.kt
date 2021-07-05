package com.app.androidhomework.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.androidhomework.di.DaggerApiComponent
import com.app.androidhomework.models.CommonModel
import com.app.androidhomework.network.APIService
import com.app.androidhomework.nw.ObserverUtil
import com.app.androidhomework.nw.WebserviceBuilder
import com.app.dripz.nw.SingleCallback
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class ForgotPasswordViewModel : ViewModel(), SingleCallback {

    val loginData = MutableLiveData<CommonModel>()
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

    fun forgotAPI(email: String) {
        loadingProgressBar.value = true
        ObserverUtil.subscribeToSingle(
            apiservice.fogotPasswordAPI(email),
            getCompositeDisposable(), WebserviceBuilder.ApiNames.Reset_Password, this
        )
    }

    override fun onSingleSuccess(o: Any?, apiNames: WebserviceBuilder.ApiNames?) {
        when (apiNames) {
            WebserviceBuilder.ApiNames.Reset_Password -> {
                loadingProgressBar.value = false
                loginError.value = false
                loginData.value = o as CommonModel
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