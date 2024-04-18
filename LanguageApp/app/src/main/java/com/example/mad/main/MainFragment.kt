package com.example.mad.main


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.mad.R
import com.example.mad.databinding.MainFragmentBinding
import com.example.mad.onBoarding.OnBoardingPrefManager
import com.example.mad.viewBinding

class MainFragment : Fragment(R.layout.main_fragment) {

    private val binding by viewBinding(MainFragmentBinding::bind)
    private lateinit var prefManager: OnBoardingPrefManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = OnBoardingPrefManager(requireContext())
        setupProfileNavigation()
        displayUserEmail()
        setupGameNavigation()
    }

    private fun setupProfileNavigation() {
        binding.profileAvatar.setOnClickListener {
            navigateToProfile()
        }
    }

    private fun displayUserEmail() {
        binding.profiletv.text = "${binding.profiletv.text} ${prefManager.email}"
    }

    private fun setupGameNavigation() {
        binding.firstGame.setOnClickListener {
            navigateToGame("gameone")
        }
        binding.secondGame.setOnClickListener {
            navigateToGame("gametwo")
        }
        binding.thirdGame.setOnClickListener {
            navigateToGame("gamethree")
        }
    }

    private fun navigateToProfile() {
        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://app/profile".toUri())
            .build()
        findNavController().navigate(request)
    }

    private fun navigateToGame(gameId: String) {
        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://app/$gameId".toUri())
            .build()
        findNavController().navigate(request)
    }

    private fun changeTheme() {
        if (prefManager.isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
