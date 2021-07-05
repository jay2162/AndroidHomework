package com.app.androidhomework.di

import android.app.Application
import com.app.androidhomework.BuildConfig
import com.app.androidhomework.network.API
import com.app.androidhomework.network.APIService
import com.app.androidhomework.network.SessionManager
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    private val BASE_URL = " https://blooming-stream-45371.herokuapp.com/"


    @Provides
    fun provideAPI(): API {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .setLenient()
            .serializeNulls().create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(API::class.java)
    }

    @Provides
    fun getClient(interceptor: Interceptor?): Retrofit? {
        var retrofit: Retrofit? = null
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .serializeNulls().create()
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit
    }


    @Provides
    fun provideService(): APIService {
        return APIService()
    }

    @Singleton
    @Provides
    fun getSecurePref(app: Application) = SessionManager(app.applicationContext)
}