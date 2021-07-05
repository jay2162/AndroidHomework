package com.app.dripz.nw

import com.app.androidhomework.nw.WebserviceBuilder


interface ListCallback {
    /**
     * @param t        returns a single object from a list of objects
     */
    fun onListNext(t: Any?, apiNames: WebserviceBuilder.ApiNames?)

    /**
     * @param throwable returns Throwable for checking Exception
     */
    fun onFailure(throwable: Throwable?, apiNames: WebserviceBuilder.ApiNames?)

    /**
     * gives an event of call completion
     *
     */
    fun onListComplete(apiNames: WebserviceBuilder.ApiNames?)
}