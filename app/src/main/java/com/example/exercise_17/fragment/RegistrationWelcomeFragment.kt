package com.example.exercise_17.fragment

import androidx.navigation.fragment.findNavController
import com.example.exercise_17.BaseFragment
import com.example.exercise_17.databinding.FragmentRegistrationWelcomeBinding


class RegistrationWelcomeFragment : BaseFragment<FragmentRegistrationWelcomeBinding>(FragmentRegistrationWelcomeBinding::inflate) {
    override fun clickListeners() {
        with(binding) {
            btnRegister.setOnClickListener {
                navigateToRegisterFragment()
            }
            btnLogin.setOnClickListener {
                navigateToLoginFragment()
            }
        }
    }

    override fun bindObserves() {

    }

    private fun navigateToRegisterFragment() {
       val action = RegistrationWelcomeFragmentDirections.actionRegistrationWelcomeFragmentToRegistrationRegisterFragment()
        findNavController().navigate(action)
    }

    private fun navigateToLoginFragment() {
        val action = RegistrationWelcomeFragmentDirections.actionRegistrationWelcomeFragmentToRegistrationLoginFragment()
        findNavController().navigate(action)
    }
}