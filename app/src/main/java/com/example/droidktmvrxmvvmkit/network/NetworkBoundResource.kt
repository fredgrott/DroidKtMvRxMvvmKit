/* Copyright 2018 Fred Grott

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.example.droidktmvrxmvvmkit.network

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread



abstract class NetworkBoundResource<ResultType, RequestType> @MainThread
constructor() {

    private val result = PublishSubject.create<Resource<ResultType>>()

    init {
        val dbSource = loadFromDb()
        dbSource.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ value ->
                //unsubscribe
                dbSource.unsubscribeOn(Schedulers.io())

                if (shouldFetch(value)) {
                    fetchFromNetwork()
                } else {
                    result.onNext(Resource.success(value))
                }
            })
    }

    private fun fetchFromNetwork() {
        val apiResponse = createCall()

        //send a loading event
        result.onNext(Resource.loading(null))
        apiResponse
            .subscribeOn(Schedulers.from(NETWORK_EXECUTOR))
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->

                //unsubscribe apiResponse and dbSource (if any)
                apiResponse.unsubscribeOn(Schedulers.io())

                ioThread {
                    saveCallResult(response)
                    mainThread {
                        // we specially request a new live data,
                        // otherwise we will get immediately last cached value,
                        // which may not be updated with latest results received from network.
                        val dbSource = loadFromDb()
                        dbSource.subscribeOn(Schedulers.from(NETWORK_EXECUTOR))
                            .observeOn(AndroidSchedulers.mainThread())
                            .take(1)
                            .subscribe({
                                dbSource.unsubscribeOn(Schedulers.io())
                                result.onNext(Resource.success(it))
                            })
                    }
                }

            }, { error ->
                onFetchFailed()
                result.onNext(Resource.error(error.localizedMessage, null))
            })

    }

    fun asFlowable(): Flowable<Resource<ResultType>> {
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    protected open fun onFetchFailed() {}

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): Flowable<ResultType>

    @MainThread
    protected abstract fun createCall(): Flowable<RequestType>

}