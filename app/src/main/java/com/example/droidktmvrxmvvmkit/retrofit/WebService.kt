package com.example.droidktmvrxmvvmkit.retrofit

import com.example.droidktmvrxmvvmkit.model.Repo
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path


interface WebService {

    @GET("users/{login}/repos")
    fun getRepos(@Path("login") login: String): Flowable<List<Repo>>

}