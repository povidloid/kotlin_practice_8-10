package com.example.kotlin_practice_8_10.retrofit

import retrofit2.http.GET
import retrofit2.http.Path

interface MainAPI {
    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): User
}