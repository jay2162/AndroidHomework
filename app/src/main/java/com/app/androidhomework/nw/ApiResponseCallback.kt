package com.app.dripz.nw

import com.app.androidhomework.nw.WebserviceBuilder


interface ApiResponseCallback {
    /**
     * @param o        Whole response Object
     */
    fun onSingleSuccess(o: Any?, apiNames: WebserviceBuilder.ApiNames?, pos : Int?)

    /**
     * @param throwable returns [Throwable] for checking Exception
     */
    fun onFailure(throwable: Throwable?, apiNames: WebserviceBuilder.ApiNames?, messages: String)
}