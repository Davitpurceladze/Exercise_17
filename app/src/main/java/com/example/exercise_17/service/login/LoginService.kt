package com.example.exercise_17.service.login

import com.example.exercise_17.fragment.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("login")
    suspend fun loginUser(@Body user: User): Response<LoginResponseType>

}