package com.app.androidhomework.utils

import com.app.androidhomework.models.ErrorModel


sealed class Response<T> {

    data class Result<T>(val result: T) : Response<T>()

    data class Error<T>(val throwable: Throwable) : Response<ErrorModel>()
}