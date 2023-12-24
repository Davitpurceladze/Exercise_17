package com.example.exercise_17.fragment

import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.exercise_17.BaseFragment
import com.example.exercise_17.databinding.FragmentRegistrationRegisterBinding
import com.example.exercise_17.viewmodel.RegisterViewModel
import kotlinx.coroutines.launch

class RegistrationRegisterFragment :
    BaseFragment<FragmentRegistrationRegisterBinding>(FragmentRegistrationRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun clickListeners() {
        binding.btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun showErrorMessage() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerFlow.collect{ it ->
                    it?.errorMessage?.let {
                        println(it )
                    }
                }
            }
        }
    }

    private fun registerUser() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val repeatPassword = binding.etRepeatPassword.text.toString()

        viewModel.registerUser(email , password, repeatPassword )

        showErrorMessage()

    }


     private fun sentDataToLoginFragment() {
             val email = binding.etEmail.text.toString()
             setFragmentResult("requestKey", bundleOf("bundleKey" to email))

            val password = binding.etPassword.text.toString()
            setFragmentResult("passwordKey", bundleOf("password" to password))

     }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerFlow.collect{ it ->
                    it?.data?.let {
                        sentDataToLoginFragment()
                        findNavController().navigate(RegistrationRegisterFragmentDirections.actionRegistrationRegisterFragmentToRegistrationLoginFragment())
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerFlow.collect{
                    if(it?.isLoading == true) {
                          with(binding) {
                              progressBar.visibility = ViewGroup.VISIBLE
                              etEmail.visibility = ViewGroup.GONE
                              etRegister.visibility = ViewGroup.GONE
                              etPassword.visibility = ViewGroup.GONE
                              etRepeatPassword.visibility = ViewGroup.GONE
                              imgEmail.visibility = ViewGroup.GONE
                              imgPassword.visibility = ViewGroup.GONE
                              imgRepeatPassword.visibility = ViewGroup.GONE
                          }
                    } else {
                        with(binding) {
                            progressBar.visibility = ViewGroup.GONE
                            etEmail.visibility = ViewGroup.VISIBLE
                            etRegister.visibility = ViewGroup.VISIBLE
                            etPassword.visibility = ViewGroup.VISIBLE
                            etRepeatPassword.visibility = ViewGroup.VISIBLE
                            imgEmail.visibility = ViewGroup.VISIBLE
                            imgPassword.visibility = ViewGroup.VISIBLE
                            imgRepeatPassword.visibility = ViewGroup.VISIBLE

                        }
                    }
                }
            }
        }
    }
}