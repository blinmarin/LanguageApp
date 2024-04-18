package com.example.mad.onBoarding

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.mad.R
import com.example.mad.databinding.OnboardingBinding
import com.example.mad.onBoarding.customView.OnBoardingView
import com.example.mad.viewBinding

class OnBoardingFragment : Fragment(R.layout.onboarding) {

    private val binding by viewBinding(OnboardingBinding::bind)
    private lateinit var onBoardingView: OnBoardingView
    private lateinit var prefManager: OnBoardingPrefManager


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBoardingView = binding.container as OnBoardingView
        onBoardingView.setNavController(findNavController())
        prefManager = OnBoardingPrefManager(requireContext())

        if (!prefManager.isFirstTimeLaunch) {
            val request = NavDeepLinkRequest.Builder
                .fromUri("android-app://app/language".toUri())
                .build()
            findNavController().navigate(request)
        }
    }
}