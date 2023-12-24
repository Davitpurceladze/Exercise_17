package com.example.exercise_17.network

import com.example.exercise_17.service.login.LoginService
import com.example.exercise_17.service.register.RegisterService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Network {

    private const val BASE_URL = "https://reqres.in/api/"
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    fun registerUserService(): RegisterService = retrofit.create(RegisterService::class.java)
    fun loginUserService(): LoginService = retrofit.create(LoginService::class.java)
}