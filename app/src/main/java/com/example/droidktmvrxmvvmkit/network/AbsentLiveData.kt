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

import com.example.droidktmvrxmvvmkit.veiwmodel.RepoLiveData
import com.example.droidktmvrxmvvmkit.repository.RepoRepository

class AbsentLiveData private constructor(repository: RepoRepository, string: String) : RepoLiveData(repository, string) {
    init {
        postValue(null)
    }

    companion object {
        fun create(repository: RepoRepository): AbsentLiveData {
            return AbsentLiveData(repository, "")
        }
    }
}