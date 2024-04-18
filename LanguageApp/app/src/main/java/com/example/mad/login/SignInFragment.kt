package com.example.mad.login

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.mad.R
import com.example.mad.databinding.LoginFragmentBinding
import com.example.mad.onBoarding.OnBoardingPrefManager
import com.example.mad.viewBinding
import java.util.Locale

class SignInFragment : Fragment(R.layout.login_fragment) {

    private val binding by viewBinding(LoginFragmentBinding::bind)
    private lateinit var prefManager: OnBoardingPrefManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = OnBoardingPrefManager(requireContext())
        setupListeners()
        binding.emailInput.addTextChangedListener(emailTextWatcher)
        setLanguage()
    }

    private fun setLanguage() {
        val languageCode = if (prefManager.isRussianActive) "ru" else "en"
        setLocale(requireContext(), languageCode)
        updateScreen()
    }

    private fun updateScreen() {
        with(binding) {
            signup.text = getString(R.string.signup)
            forFree.text = getString(R.string.for_free_join_now_and_n_start_learning)
            email.text = getString(R.string.email_address)
            password.text = getString(R.string.password)
            forgotPassword.text = getString(R.string.forgot_password)
            loginButton.text = getString(R.string.login)
            notYourMember.text = getString(R.string.not_you_member)
            signupLink.text = getString(R.string.signup_small)
            useCanUse.text = getString(R.string.use_can_use)
            googleBtn.text = getString(R.string.google_for)
            signSmall.text = getString(R.string.sign_in_small)
        }
    }

    private fun setupListeners() {
        binding.seepass.setOnClickListener { togglePasswordVisibility() }
        binding.loginButton.setOnClickListener { validateAndLogin() }
        binding.signupLink.setOnClickListener { navigateToSignUp() }
        binding.back.setOnClickListener { navigateToLanguageSelection() }
    }

    private val emailTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            validateEmail()
        }
    }

    private fun togglePasswordVisibility() {
        val passwordInputType = if (binding.passwordInput.transformationMethod == PasswordTransformationMethod.getInstance())
            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        else
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        binding.passwordInput.transformationMethod = PasswordTransformationMethod.getInstance()
        binding.passwordInput.inputType = passwordInputType
        binding.passwordInput.setSelection(binding.passwordInput.text.length)
    }

    private fun validateEmail(): Boolean {
        val emailInput = binding.emailInput.text.toString().trim()
        val emailPattern = Regex("^[a-z0-9]+@[a-z0-9]+\\.[a-z]{2,4}\$")
        return if (emailInput.matches(emailPattern)) {
            binding.emailInput.error = null
            true
        } else {
            binding.emailInput.error = "Некорректный адрес электронной почты"
            false
        }
    }

    private fun validateAndLogin() {
        if (binding.emailInput.text.toString().isNotEmpty() && validateEmail() && binding.passwordInput.text != null) {
            prefManager.isAuth = true
            prefManager.email = binding.emailInput.text.toString().trim()
            navigateToMainScreen()
        } else {
            showToast("Ошибка аутентификации")
        }
    }

    private fun navigateToMainScreen() {
        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://app/main".toUri())
            .build()
        findNavController().popBackStack()
        findNavController().navigate(request)
    }

    private fun navigateToSignUp() {
        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://app/signup".toUri())
            .build()
        findNavController().navigate(request)
    }

    private fun navigateToLanguageSelection() {
        prefManager.isLanguageChosen = false
        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://app/language".toUri())
            .build()
        findNavController().navigate(request)
    }

    fun setLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)
        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}