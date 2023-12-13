package com.example.exercise_17.common

sealed class Resource<T>(
    val data: T? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
) {
    data class Success<T>(val value: T): Resource<T>(data = value)
    data class Error<T>(val error: String): Resource<T>(errorMessage = error)
    data class Loading<T>(val loading: Boolean): Resource<T>(isLoading = loading)
}