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
package com.example.droidktmvrxmvvmkit.repository

import androidx.annotation.Nullable

import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

import com.example.droidktmvrxmvvmkit.db.RepoDao
import com.example.droidktmvrxmvvmkit.retrofit.WebService
import com.example.droidktmvrxmvvmkit.utils.RateLimiter
import com.example.droidktmvrxmvvmkit.network.Resource
import com.example.droidktmvrxmvvmkit.model.Repo
import com.example.droidktmvrxmvvmkit.network.NetworkBoundResource

class RepoRepository(val repoDao: RepoDao, val webService: WebService) {
    val repoListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)

    fun loadRepos(owner: String): Flowable<Resource<List<Repo>>> {
        return object : NetworkBoundResource<List<Repo>, List<Repo>>() {
            override fun saveCallResult(item: List<Repo>) {
                repoDao.insertRepos(item)
            }

            override fun shouldFetch(@Nullable data: List<Repo>?): Boolean {
                return (data == null || data.isEmpty()
                        || repoListRateLimit.shouldFetch(owner))
            }

            override fun loadFromDb(): Flowable<List<Repo>> {
                return repoDao.loadRepositories(owner)
            }

            override fun createCall(): Flowable<List<Repo>> {
                return webService.getRepos(owner)
            }

            override fun onFetchFailed() {
                repoListRateLimit.reset(owner)
            }
        }.asFlowable()
    }

}