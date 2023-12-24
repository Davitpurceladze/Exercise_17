package com.example.exercise_17.fragment

import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.exercise_17.BaseFragment
import com.example.exercise_17.databinding.FragmentRegistrationLoginBinding
import com.example.exercise_17.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegistrationLoginFragment :
    BaseFragment<FragmentRegistrationLoginBinding>(FragmentRegistrationLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()
    override fun clickListeners() {
        binding.btnLogin.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        viewModel.loginUser(email, password)
        showErrorMessage()

    }
    private fun showErrorMessage() {
        viewLifecycleOwner.lifecycleScope.launch { 
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.loginFlow.collect{ it ->
                    it?.errorMessage?.let { 
                        println(it)
                    }
                }
            }
        }
    }

    override fun bindObserves() {
        setDataFromRegisterFragment()
         
    }

    private fun setDataFromRegisterFragment() {
        setFragmentResultListener("requestKey") { _, bundle ->
            val result = bundle.getString("bundleKey")
            binding.etEmail.setText(result)
        }

        setFragmentResultListener("passwordKey") { _, bundle ->
            val result = bundle.getString("password")
            binding.etPassword.setText(result)
        }
    }
}