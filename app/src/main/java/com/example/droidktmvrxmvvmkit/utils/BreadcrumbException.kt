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

import io.reactivex.Observable
import io.reactivex.exceptions.CompositeException

/*
  Use by
  just("a string").map { null }
.subscribeOn(Schedulers.io())
.dropBreadcrumb()
.subscribe()

 */
inline fun <T> Observable<T>.dropBreadcrumb(): Observable<T> {
    val breadcrumb = BreadcrumbException()
    return this.onErrorResumeNext { error: Throwable ->
        throw CompositeException(error, breadcrumb)
    }
}
class BreadcrumbException : Exception()