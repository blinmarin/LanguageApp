package com.example.mad.signUp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.mad.R
import com.example.mad.databinding.SignupFragmentBinding
import com.example.mad.onBoarding.OnBoardingPrefManager
import com.example.mad.viewBinding

class SignUpFragment : Fragment(R.layout.signup_fragment) {

    private val binding by viewBinding(SignupFragmentBinding::bind)
    private lateinit var prefManager: OnBoardingPrefManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = OnBoardingPrefManager(requireContext())
        setupViews()
    }

    private fun setupViews() {
        saveState()
        binding.emailInput.doAfterTextChanged {
            validateEmail()
        }
        binding.loginButton.setOnClickListener {
            if (validateFields()) {
                navigateToSignUpPassword()
            }
        }
        binding.signupLink.setOnClickListener {
            navigateToLogin()
        }
        binding.back.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun validateFields(): Boolean {
        val emailValid = validateEmail()
        val firstName = binding.firstnameInput.text.toString().trim()
        val lastName = binding.lastnameInput.text.toString().trim()

        val isFirstNameValid = validateField(firstName, "First Name")
        val isLastNameValid = validateField(lastName, "Last Name")

        return emailValid && isFirstNameValid && isLastNameValid
    }

    private fun validateField(value: String, fieldName: String): Boolean {
        return if (value.isNotEmpty()) {
            true
        } else {
            showToast("$fieldName cannot be empty")
            false
        }
    }

    private fun validateEmail(): Boolean {
        val emailInput = binding.emailInput.text.toString().trim()
        val emailPattern = Regex("^[a-z0-9]+@[a-z0-9]+\\.[a-z]{2,4}\$")
        return if (emailInput.matches(emailPattern)) {
            binding.emailInput.error = null
            true
        } else {
            binding.emailInput.error = "Неправильно введен Email"
            false
        }
    }

    private fun saveState() {
        with(binding) {
            firstnameInput.setText(prefManager.firstName)
            lastnameInput.setText(prefManager.lastName)
            emailInput.setText(prefManager.email)

            firstnameInput.doAfterTextChanged {
                prefManager.firstName = it.toString()
            }
            lastnameInput.doAfterTextChanged {
                prefManager.lastName = it.toString()
            }
            emailInput.doAfterTextChanged {
                prefManager.email = it.toString()
            }
        }
    }

    private fun navigateToSignUpPassword() {
        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://app/signupPass".toUri())
            .build()
        findNavController().navigate(request)
    }

    private fun navigateToLogin() {
        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://app/login".toUri())
            .build()
        findNavController().navigate(request)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}