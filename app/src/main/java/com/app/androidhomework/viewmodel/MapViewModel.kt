package com.app.androidhomework.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.androidhomework.di.DaggerApiComponent
import com.app.androidhomework.models.MapModel
import com.app.androidhomework.models.MapModelItem
import com.app.androidhomework.network.APIService
import com.app.androidhomework.nw.ObserverUtil
import com.app.androidhomework.nw.WebserviceBuilder

import com.app.dripz.nw.SingleCallback
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class MapViewModel : ViewModel(), SingleCallback {

    val myPostData = MutableLiveData<ArrayList<MapModelItem>>()
    val myPostError = MutableLiveData<Boolean>()
    val apiError = MutableLiveData<String>()
    val loadingProgressBar = MutableLiveData<Boolean>()
    private var compositeDisposable: CompositeDisposable? = null

    @Inject
    lateinit var apiservice: APIService
    val disposable = CompositeDisposable()

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun MapAPI(token: String) {
        loadingProgressBar.value = true
        ObserverUtil.subscribeToSingle(
            apiservice.mapAPI(token),
            getCompositeDisposable(), WebserviceBuilder.ApiNames.common, this
        )
    }

    override fun onSingleSuccess(o: Any?, apiNames: WebserviceBuilder.ApiNames?) {
        when (apiNames) {
            WebserviceBuilder.ApiNames.common -> {
                loadingProgressBar.value = false
                myPostError.value = false
                myPostData.value = o as ArrayList<MapModelItem>
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
        myPostError.value = true
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