package com.example.exercise_17.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exercise_17.common.Resource
import com.example.exercise_17.exceptionHandling.ExceptionHandling
import com.example.exercise_17.fragment.User
import com.example.exercise_17.network.Network
import com.example.exercise_17.service.login.LoginResponseType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginFlow = MutableStateFlow<Resource<LoginResponseType>?>(null)

    val loginFlow: StateFlow<Resource<LoginResponseType>?> get() = _loginFlow

    fun loginUser(email: String, password: String) {

        val reqresSuffix = "@reqres.in"
        if (!(email.endsWith(reqresSuffix, ignoreCase = true))) {
            _loginFlow.value = Resource.Error("email should be filled")
        } else if (password.isEmpty()) {
            _loginFlow.value = Resource.Error("check password")
        } else {

            viewModelScope.launch {
//                Loading
                try {
                    val response = Network.loginUserService().loginUser(User(email, password))
                    if(response.isSuccessful) {
                        _loginFlow.value = response.body()?.let {
                            Resource.Success(LoginResponseType(it.token))
                        }
                        println(response.body())
                    } else if (response.code() in 400..499) {
                        _loginFlow.value = Resource.Error(response.message())
                    }
                } catch (e: Exception) {
                    val error = ExceptionHandling.exceptionHandler(e)
                    _loginFlow.value = Resource.Error(error)
                }
//                loading
            }
        }

    }
}