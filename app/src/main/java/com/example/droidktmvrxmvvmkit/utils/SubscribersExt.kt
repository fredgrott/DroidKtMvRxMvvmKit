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
package com.example.droidktmvrxmvvmkit.utils

import com.example.droidktmvrxmvvmkit.App
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

import com.google.gson.Gson
import com.github.ajalt.timberkt.*



/*
   Instead of call stacks I use TimberKT
   that way things stay in debug logs only

   so now we use this subscribeBy ext instead
 */
fun Completable.subscribeBy(
    onError: (Throwable) -> Unit,
    onComplete: () -> Unit
): Disposable = this
    .doOnError { it.logRxOnError() }
    .subscribeBy(onError, onComplete)

fun <T : Any> Single<T>.subscribeBy(
    onError: (Throwable) -> Unit,
    onSuccess: (T) -> Unit
): Disposable = this
    .doOnSuccess { it.logRxOnSuccess() }
    .doOnError { it.logRxOnError() }
    .subscribeBy(onError, onSuccess)

fun <T : Any> Observable<T>.subscribeBy(
    onError: (Throwable) -> Unit,
    onComplete: () -> Unit,
    onNext: (T) -> Unit
): Disposable = this
    .doOnNext { it.logRxOnNext() }
    .doOnError { it.logRxOnError() }
    .subscribeBy(onError, onComplete, onNext)

fun <T : Any> Maybe<T>.subscribeBy(
    onError: (Throwable) -> Unit,
    onComplete: () -> Unit,
    onSuccess: (T) -> Unit
): Disposable = this
    .doOnSuccess { it.logRxOnSuccess() }
    .doOnError { it.logRxOnError() }
    .subscribeBy(onError, onComplete, onSuccess)


private fun Any.logRxOnSuccess() = Timber.tag("${App.TAG} onSuccess").d(toReadableResult())

private fun Any.logRxOnNext() = Timber.tag( "${App.TAG} onNext").d(toReadableResult())

private fun Throwable.logRxOnError() = Timber.tag("${App.TAG} OnError").d(message, cause)

private fun Any.toReadableResult() =
    "${if (this is List<*> && this.isNotEmpty()) "${javaClass.simpleName}<${this.first()?.javaClass?.simpleName}>"
    else javaClass.simpleName} - ${Gson().toJson(this)}"