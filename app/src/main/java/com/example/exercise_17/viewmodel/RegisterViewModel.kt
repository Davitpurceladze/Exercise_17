package com.example.exercise_17.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exercise_17.common.Resource
import com.example.exercise_17.exceptionHandling.ExceptionHandling
import com.example.exercise_17.fragment.User
import com.example.exercise_17.network.Network
import com.example.exercise_17.service.register.RegisterResponseType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _registerFlow = MutableStateFlow<Resource<RegisterResponseType>?>(null)
    val registerFlow: StateFlow<Resource<RegisterResponseType>?> get() = _registerFlow
    fun registerUser(email: String, password: String, repeatPassword: String) {
        val reqresSuffix = "@reqres.in"
        if (!(email.endsWith(reqresSuffix, ignoreCase = true))) {
            _registerFlow.value = Resource.Error("email should end with @reqres.in")
        } else if (password == ""  || password != repeatPassword) {
            _registerFlow.value = Resource.Error("check password fields")
        } else {
            viewModelScope.launch {
                _registerFlow.value = Resource.Loading(true)
                try {
                    val response = Network.registerUserService().registerUser(User(email, password))
                    if (response.isSuccessful) {
                        _registerFlow.value = response.body()?.let {
                            Resource.Success(RegisterResponseType(it.id, it.token))

                        }
                    } else if (response.code() in 400..499) {
                        _registerFlow.value = Resource.Error(response.message())
                    }
                } catch (e: Exception) {
                    val error = ExceptionHandling.exceptionHandler(e)
                    _registerFlow.value = Resource.Error(error)
                } finally {
                    _registerFlow.value = Resource.Loading(false)
                }
            }
        }
    }
}