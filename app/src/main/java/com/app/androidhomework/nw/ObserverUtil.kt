package com.app.androidhomework.nw

import com.app.dripz.nw.ApiResponseCallback
import com.app.dripz.nw.ListCallback
import com.app.dripz.nw.SingleCallback
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException


object ObserverUtil {
    /**
     * @param observable          make it via ApiClient.getClient().create(Class_Name).method()
     * @param compositeDisposable get it from base Activity or base Fragment
     * @param apiNames            APIName which will help identify it
     * @param callback            to receive callback
     */
    fun <T> subscribeToList(observable: Observable<List<T>>, compositeDisposable: CompositeDisposable?, apiNames: WebserviceBuilder.ApiNames?, callback: ListCallback?) {
        makeObservable(observable)
            .flatMap { ts -> Observable.fromIterable(ts) }
            .subscribe(getObserver<T>(compositeDisposable, apiNames, callback))
    }

    /**
     * @param observable make it via ApiClient.getClient().create(Class_Name).method()
     * @return Observable which helps in adding other functions like flatMap(), filter()
     */
    fun <T> makeObservable(observable: Observable<T>): Observable<T> {
        return observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    /**
     * @return Observer with which observable will subscribe
     */
    fun <T> getObserver(compositeDisposable: CompositeDisposable?, apiNames: WebserviceBuilder.ApiNames?, callback: ListCallback?): Observer<T> {
        return object : Observer<T> {
            override fun onSubscribe(d: Disposable) {
                compositeDisposable?.add(d)
            }

            override fun onNext(value: T) {
                callback?.onListNext(value, apiNames)
            }

            override fun onError(e: Throwable) {
                callback?.onFailure(e, apiNames)
            }

            override fun onComplete() {
                callback?.onListComplete(apiNames)
            }
        }
    }

    /**
     * @param observable          make it via ApiClient.getClient().create(Class_Name).method()
     * @param compositeDisposable get it from base Activity or base Fragment
     * @param apiNames            APIName which will help identify it
     * @param singleCallback      to receive callback
     */
    fun <T> subscribeToSingle(observable: Observable<T>?, compositeDisposable: CompositeDisposable?, apiNames: WebserviceBuilder.ApiNames?, singleCallback: SingleCallback) {
        makeSingle(observable).subscribe(
            getSingleObserver<T>(compositeDisposable, apiNames, singleCallback)
        )
    }

    /**
     * @param observable make it via ApiClient.getClient().create(Class_Name).method()
     * @return Single which helps in adding other functions like flatMap(), filter()
     */
    fun <T> makeSingle(observable: Observable<T>?): Single<T> {
        return Single.fromObservable(observable)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    /**
     * @return SingleObserver with which observable will subscribe
     */
    fun <T> getSingleObserver(compositeDisposable: CompositeDisposable?, apiNames: WebserviceBuilder.ApiNames?, tSingleCallback: SingleCallback?): SingleObserver<T> {
        return object : SingleObserver<T> {
            override fun onSubscribe(d: Disposable) {
                compositeDisposable?.add(d)
            }

            override fun onSuccess(t: T) {
                tSingleCallback?.onSingleSuccess(t, apiNames)
            }

            override fun onError(throwable: Throwable) {
                var messages = "An error occurred"
                if (throwable is HttpException) {
                    val errorJsonString = throwable.response()!!.errorBody()?.string()
                    var jsonElement : JsonElement = JsonParser().parse(errorJsonString)
                    val json = jsonElement.asJsonObject

                    if (json.has("errors") && !json.get("errors").isJsonNull) {
                        try {
                            val obj = json.asJsonObject["errors"].toString()
                            val objE = JSONObject(obj)
                            val iterator = objE.keys()
                            if (iterator.hasNext()) {
                                val key: String = iterator.next().toString()
                                messages = objE.get(key).toString().replace("[\"","\"]").replace("\"]","")
                            }
                        } catch (e: Exception) {
                           e.printStackTrace()
                        }
                    }else if(json.has("message") && !json.get("message").isJsonNull){
                        messages = jsonElement.asJsonObject["message"]
                            .asString
                    }else if(json.has("data") && !json.get("data").isJsonNull){
                        messages = jsonElement.asJsonObject["data"]
                            .asString
                    }

                } else {
                    //messages = (throwable as HttpException).message ?: messages
                    messages = throwable.message.toString()
                }
                println("messages:- $messages")
                //println("Error:- " + throwable.response()!!.errorBody())
                tSingleCallback?.onFailure(throwable, apiNames, messages)
            }
        }
    }


    /**
     * @param observable          make it via ApiClient.getClient().create(Class_Name).method()
     * @param compositeDisposable get it from base Activity or base Fragment
     * @param apiNames            APIName which will help identify it
     * @param singleCallback      to receive callback
     */
    fun <T> subscribeToSingleWithPos(observable: Observable<T>?, compositeDisposable: CompositeDisposable?, apiNames: WebserviceBuilder.ApiNames?, singleCallback: ApiResponseCallback?, pos: Int?) {
        makeSingle(observable).subscribe(
            getSingleObserver<T>(compositeDisposable, apiNames, singleCallback,pos)
        )
    }

    /**
     * @return SingleObserver with which observable will subscribe
     */
    fun <T> getSingleObserver(compositeDisposable: CompositeDisposable?, apiNames: WebserviceBuilder.ApiNames?, tSingleCallback: ApiResponseCallback?, pos: Int?): SingleObserver<T> {
        return object : SingleObserver<T> {
            override fun onSubscribe(d: Disposable) {
                compositeDisposable?.add(d)
            }

            override fun onSuccess(t: T) {
                tSingleCallback?.onSingleSuccess(t, apiNames,pos)
            }

            override fun onError(throwable: Throwable) {
                var messages = "An error occurred"
                if (throwable is HttpException) {
                    val errorJsonString = throwable.response()!!.errorBody()?.string()
                    var jsonElement : JsonElement = JsonParser().parse(errorJsonString)
                    val json = jsonElement.asJsonObject

                    if (json.has("errors") && !json.get("errors").isJsonNull) {
                        try {
                            val obj = json.asJsonObject["errors"].toString()
                            val objE = JSONObject(obj)
                            val iterator = objE.keys()
                            if (iterator.hasNext()) {
                                val key: String = iterator.next().toString()
                                messages = objE.get(key).toString().replace("[\"","\"]").replace("\"]","")
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }else if(json.has("message") && !json.get("message").isJsonNull){
                        messages = jsonElement.asJsonObject["message"]
                            .asString
                    }else if(json.has("data") && !json.get("data").isJsonNull){
                        messages = jsonElement.asJsonObject["data"]
                            .asString
                    }

                } else {
                    //messages = (throwable as HttpException).message ?: messages
                    messages = throwable.message.toString()
                }
                println("messages:- $messages")
                //println("Error:- " + throwable.response()!!.errorBody())
                tSingleCallback?.onFailure(throwable, apiNames, messages)
            }
        }
    }
}