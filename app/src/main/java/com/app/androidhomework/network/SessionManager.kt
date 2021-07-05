package com.app.androidhomework.network

import android.content.Context
import android.content.SharedPreferences
import com.app.androidhomework.R
import com.app.androidhomework.models.AuthenticationModel
import com.google.gson.Gson

class SessionManager(context: Context) {
    var editor: SharedPreferences.Editor
    private val pref: SharedPreferences
    var PREF_DEVICE_TOKEN = "device_token"

    /**
     * Set Boolean Value
     * */
    fun getBooleanDataByKey(key: String?): Boolean {
        return pref.getBoolean(key, false)
    }

    fun setBooleanDataByKey(key: String?, isTrue: Boolean) {
        editor.putBoolean(key, isTrue)
        editor.commit()
    }

    fun getDeviceToken(): String? {
        return pref.getString(
            PREF_DEVICE_TOKEN,
            "d3aF92DsTPCQ7KBR-Ad38h%3AAPA91bHHAT8HrXSeo_XivwJxFzO80WsDu-P1Yk1F8-IcHuRciiN2npxHcz40wmLLg5TbbZJOAwvB03R791EssjUaGfVq8uyHPn9GsWT1La-IDZ3MAb9sY4CWgTdoUa6yGdgY5F61t3mG"
        )
    }

    fun setStringDataByKey(key: String?, value: String) {
        editor.putString(key, value)
        editor.commit()
    }

    /**
     * Set logout
     * */
    fun setLogout() {
        editor.clear().apply()
    }


    var isNotify: Boolean?
        get() = pref.getBoolean(KEY_IS_NOTIFY, false)
        set(isNotify) {
            editor.putBoolean(KEY_IS_NOTIFY, isNotify!!)
            editor.commit()
        }

    /**
     * Set AuthenticationModel Model
     * */

    fun saveAuthorizedUser(authorizedUser: AuthenticationModel?) {
        val json = Gson().toJson(authorizedUser)
        editor.putString(KEY_AUTHORIZED_USER, json).apply()
    }

    fun getAuthorizedUser(): AuthenticationModel? {
        val json = pref.getString(KEY_AUTHORIZED_USER, "")
        return Gson().fromJson(json, AuthenticationModel::class.java)
    }

    companion object {
        const val KEY_AUTHORIZED_USER = "KEY_AUTHORIZED_USER"
        const val KEY_IS_NOTIFY = "is_notify"

    }

    init {
        val PREF_NAME = context.resources.getString(R.string.app_name)
        val PRIVATE_MODE = 0
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

}