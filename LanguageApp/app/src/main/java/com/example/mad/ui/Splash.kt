package com.example.mad.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.mad.R
import com.example.mad.databinding.FragmentSplashBinding
import com.example.mad.viewBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class Splash : Fragment(R.layout.fragment_splash) {

    private val binding by viewBinding(FragmentSplashBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(2000)
            val request = NavDeepLinkRequest.Builder
                .fromUri("android-app://app/onboarding".toUri())
                .build()
            findNavController().navigate(request)
        }
    }

}