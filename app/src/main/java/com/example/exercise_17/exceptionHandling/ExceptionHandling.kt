package com.example.exercise_17.exceptionHandling

import okio.IOException
import retrofit2.HttpException
import java.util.concurrent.TimeoutException

object ExceptionHandling {

    fun exceptionHandler(e: Throwable): String {
        return when (e) {
            is IOException -> "Network Error"

            is UnsupportedOperationException -> "Operation is not supported"

            is HttpException -> e.message()

            is TimeoutException -> "${e.message}"

            else -> "in case of unexpected error, contact support"
        }
    }
}