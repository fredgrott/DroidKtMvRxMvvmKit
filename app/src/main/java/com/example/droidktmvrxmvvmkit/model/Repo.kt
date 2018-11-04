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