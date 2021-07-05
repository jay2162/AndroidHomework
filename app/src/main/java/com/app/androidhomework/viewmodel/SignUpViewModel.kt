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

class SignUpViewModel : ViewModel(), SingleCallback {

    val signupData = MutableLiveData<AuthenticationModel>()
    val signupError = MutableLiveData<Boolean>()
    val apiError = MutableLiveData<String>()
    val loadingProgressBar = MutableLiveData<Boolean>()
    private var compositeDisposable: CompositeDisposable? = null

    @Inject
    lateinit var apiservice: APIService
    val disposable = CompositeDisposable()

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun signupAPI(
        name: String,
        email: String,
        password: String) {
        loadingProgressBar.value = true
        subscribeToSingle(
            apiservice.signAPI(name, email, password),
            getCompositeDisposable(), WebserviceBuilder.ApiNames.Signup, this
        )
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

    override fun onSingleSuccess(o: Any?, apiNames: WebserviceBuilder.ApiNames?) {
        when (apiNames) {
            WebserviceBuilder.ApiNames.Signup -> {
                loadingProgressBar.value = false
                signupError.value = false
                signupData.value = o as AuthenticationModel
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
        signupError.value = true
    }
}