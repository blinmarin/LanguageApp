package com.example.mad.signUp

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.mad.R
import com.example.mad.databinding.SignupTwoFragmentBinding
import com.example.mad.onBoarding.OnBoardingPrefManager
import com.example.mad.viewBinding

class SignUpTwoFragment : Fragment(R.layout.signup_two_fragment) {

    private val binding by viewBinding(SignupTwoFragmentBinding::bind)
    private lateinit var prefManager: OnBoardingPrefManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = OnBoardingPrefManager(requireContext())
        setupListeners()
        setupButtons()
    }

    private fun setupListeners() {
        binding.seepass.setOnClickListener {
            togglePasswordVisibility(binding.passwordInput)
        }
        binding.seepass2.setOnClickListener {
            togglePasswordVisibility(binding.passwordInput2)
        }
    }

    private fun setupButtons() {
        binding.loginButton.setOnClickListener {
            navigateToMain()
        }
        binding.signupLink.setOnClickListener {
            navigateToLogin()
        }
        binding.back.setOnClickListener {
            navigateToSignUp()
        }
    }

    private fun togglePasswordVisibility(editText: EditText) {
        val isPasswordVisible = editText.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        editText.inputType =
            if (isPasswordVisible) InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            else InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
    }

    private fun navigateToMain() {
        prefManager.isAuth = true
        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://app/main".toUri())
            .build()
        findNavController().navigate(request)
    }

    private fun navigateToLogin() {
        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://app/login".toUri())
            .build()
        findNavController().navigate(request)
    }

    private fun navigateToSignUp() {
        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://app/signup".toUri())
            .build()
        findNavController().navigate(request)
    }
}