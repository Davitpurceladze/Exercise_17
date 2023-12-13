package com.example.exercise_17.service.register

import com.example.exercise_17.fragment.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {

    @POST("register")
    suspend fun registerUser(@Body user: User): Response<RegisterResponseType>
}