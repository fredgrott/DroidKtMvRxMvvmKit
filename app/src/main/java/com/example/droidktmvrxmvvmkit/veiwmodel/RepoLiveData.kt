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

import io.reactivex.disposables.Disposable
import com.example.droidktmvrxmvvmkit.network.Resource
import com.example.droidktmvrxmvvmkit.repository.RepoRepository
import com.example.droidktmvrxmvvmkit.model.Repo





open class RepoLiveData(repoRepository: RepoRepository, owner:String):
    LiveData<Resource<List<Repo>>>() {
    private var disposable: Disposable? = null

    init {
        disposable = repoRepository.loadRepos(owner).subscribe({
                data ->
            value = data
        })
    }

    override fun onInactive() {
        super.onInactive()
        if(disposable?.isDisposed?.not() == true){
            disposable?.dispose()
        }
    }
}