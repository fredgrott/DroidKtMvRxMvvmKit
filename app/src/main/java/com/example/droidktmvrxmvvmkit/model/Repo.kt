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
package com.example.droidktmvrxmvvmkit.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.annotation.NonNull
import com.google.gson.annotations.SerializedName


@Entity(indices = arrayOf(Index("id"),
    Index("owner_login")),
    primaryKeys = arrayOf("name","owner_login"))
class Repo(
    var id: Int = 0,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("full_name")
    var fullName: String? = "",
    @SerializedName("description")
    var description: String? = "",
    @SerializedName("owner")
    @Embedded(prefix = "owner_")
    @NonNull
    var owner: Owner = Owner(),
    @SerializedName("stargazers_count")
    var stars: Int = 0
) {
    class Owner(
        @NonNull
        @SerializedName("login")
        var login: String = "",
        @SerializedName("url")
        var url: String? = ""
    )
}