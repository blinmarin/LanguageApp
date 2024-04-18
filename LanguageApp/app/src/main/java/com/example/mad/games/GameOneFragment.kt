package com.example.mad.games


import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.mad.R
import com.example.mad.databinding.GameOneFragmentBinding
import com.example.mad.viewBinding

class GameOneFragment : Fragment(R.layout.game_one_fragment) {

    private val binding by viewBinding(GameOneFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
    }

    private fun setupButtons() {
        binding.checkButton.setOnClickListener {
            if (binding.animalText.text.toString().toLowerCase() == "racoon") {
                showWinState()
            } else {
                showLoseState()
            }
        }

        binding.looseNextButton.setOnClickListener { resetGame() }
        binding.looseTryAgainButton.setOnClickListener { resetGame() }
        binding.winButton.setOnClickListener { resetGame() }

        binding.back.setOnClickListener { navigateToMain() }
        binding.backLoose.setOnClickListener { navigateToMain() }
        binding.backWin.setOnClickListener { navigateToMain() }
    }

    private fun showWinState() {
        binding.gameWin.visibility = View.VISIBLE
        binding.gameLoose.visibility = View.GONE
        binding.gameOne.visibility = View.GONE
    }

    private fun showLoseState() {
        binding.looseText.text = getString(R.string.that_is) + " " + "racoon"
        binding.gameWin.visibility = View.GONE
        binding.gameLoose.visibility = View.VISIBLE
        binding.gameOne.visibility = View.GONE
    }

    private fun resetGame() {
        binding.gameWin.visibility = View.GONE
        binding.gameLoose.visibility = View.GONE
        binding.gameOne.visibility = View.VISIBLE
        binding.animalText.setText("")
    }

    private fun navigateToMain() {
        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://app/main".toUri())
            .build()
        findNavController().navigate(request)
    }
}