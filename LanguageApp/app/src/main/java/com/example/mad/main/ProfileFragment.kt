package com.example.mad.main

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.mad.R
import com.example.mad.databinding.ProfileFragmentBinding
import com.example.mad.onBoarding.OnBoardingPrefManager
import com.example.mad.viewBinding
import java.util.Locale

class ProfileFragment : Fragment(R.layout.profile_fragment) {

    private val binding by viewBinding(ProfileFragmentBinding::bind)
    private lateinit var prefManager: OnBoardingPrefManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = OnBoardingPrefManager(requireContext())
        setupUI()
    }

    private fun setupUI() {
        changeTheme()
        changeLanguage()
        displayUserEmail()
        setupLogout()
    }

    private fun changeTheme() {
        binding.switchTheme.setOnClickListener {
            toggleTheme()
            toggleLanguage()
            prefManager.isDarkTheme = !prefManager.isDarkTheme
        }
        updateLogoutButton()
    }

    private fun toggleTheme() {
        val nightMode = if (prefManager.isDarkTheme) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }

    private fun changeLanguage() {
        binding.changeLanguage.setOnClickListener {
            prefManager.isLanguageChosen = false
            navigateToLanguageSelection()
        }
    }

    private fun displayUserEmail() {
        binding.profiletv.text = "${binding.profiletv.text} ${prefManager.email}"
    }

    private fun setupLogout() {
        binding.Logout.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        prefManager.isAuth = false
        navigateToLogin()
    }

    private fun navigateToLogin() {
        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://app/login".toUri())
            .build()
        findNavController().navigate(request)
    }

    private fun navigateToLanguageSelection() {
        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://app/language".toUri())
            .build()
        findNavController().navigate(request)
    }

    private fun updateLogoutButton() {
        binding.Logout.background = requireContext().resources.getDrawable(R.drawable.rounded_inactive)
    }

    private fun toggleLanguage() {
        val languageCode = if (prefManager.isRussianActive) "ru" else "en"
        setLocale(requireContext(), languageCode)
    }

    private fun setLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)
        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
    }
}