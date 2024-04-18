package com.example.mad.language

import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.mad.R
import com.example.mad.databinding.LanguageChoiceFragmentBinding
import com.example.mad.onBoarding.OnBoardingPrefManager
import com.example.mad.viewBinding
import java.util.Locale

class LanguageFragment : Fragment(R.layout.language_choice_fragment) {

    private val binding by viewBinding(LanguageChoiceFragmentBinding::bind)
    private val prefManager: OnBoardingPrefManager by lazy {
        OnBoardingPrefManager(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        if (prefManager.isLanguageChosen) {
            navigateToLogin()
        }
    }

    private fun setupListeners() {
        updateLanguageUI()

        binding.russianbtn.setOnClickListener {
            prefManager.isRussianActive = true
            setLocale("ru")
            updateLanguageUI()
        }

        binding.englishbtn.setOnClickListener {
            prefManager.isRussianActive = false
            setLocale("en")
            updateLanguageUI()
        }

        binding.choosebtn.setOnClickListener {
            prefManager.isLanguageChosen = true
            navigateToLogin()
        }
    }

    private fun updateLanguageUI() {
        val isRussianActive = prefManager.isRussianActive
        binding.russianbtn.background = getDrawableResource(if (isRussianActive) R.drawable.rounded_active else R.drawable.rounded_inactive)
        binding.englishbtn.background = getDrawableResource(if (isRussianActive) R.drawable.rounded_inactive else R.drawable.rounded_active)
        binding.russiantv.text = getString(R.string.russian)
        binding.englishtv.text = getString(R.string.english)
        binding.choosebtn.text = getString(R.string.choose)
        binding.languageSelect.text = getString(R.string.language_select)
        binding.motherLanguage.text = getString(R.string.what_is_your_mother_language)
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources = requireContext().resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    private fun navigateToLogin() {
        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://app/login".toUri())
            .build()
        findNavController().navigate(request)
    }

    private fun getDrawableResource(@DrawableRes drawableResId: Int): Drawable? {
        return ContextCompat.getDrawable(requireContext(), drawableResId)
    }
}