package com.muazekici.n11sample.data.remote_source

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubAPI {

    @GET("search/users")
    suspend fun getUsers(@Query("q") q: String): SearchResponseDTO

    @GET("users/{userName}")
    suspend fun getUserDetail(@Path("userName") userName: String): UserDetailResponseDTO
}