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
package com.example.droidktmvrxmvvmkit.veiwmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

import com.example.droidktmvrxmvvmkit.repository.RepoRepository
import com.example.droidktmvrxmvvmkit.network.Resource
import com.example.droidktmvrxmvvmkit.model.Repo
import com.example.droidktmvrxmvvmkit.network.AbsentLiveData
import java.util.*



class RepoViewModel constructor(private val repository: RepoRepository):ViewModel() {

    var currentRepoUser: String? = null
    val results: LiveData<Resource<List<Repo>>>

    private val query: MutableLiveData<String> = MutableLiveData()

    init {
        results = Transformations.switchMap(query, {
            when {
                it == null || it.length == 1 -> AbsentLiveData.create(repository)
                else -> RepoLiveData(repository, it)
            }
        })
    }

    fun setQuery(originalInput: String?, force:Boolean) {
        if(originalInput==null) return
        val input = originalInput.toLowerCase(Locale.getDefault()).trim { it <= ' ' }
        if (input == query.value && !force) {
            return
        }
        query.value = input
    }

}