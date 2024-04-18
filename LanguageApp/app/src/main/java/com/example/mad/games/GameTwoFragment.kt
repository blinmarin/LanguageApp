package com.example.mad.games

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.mad.R
import com.example.mad.databinding.GameTwoFragmentBinding
import com.example.mad.viewBinding

class GameTwoFragment : Fragment(R.layout.game_two_fragment) {

    private val binding by viewBinding(GameTwoFragmentBinding::bind)
    private var clicked: Int = 0
    private val correctGame2 = 3

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setText()
    }

    private fun setupButtons() {
        binding.check.setOnClickListener {
            evaluateAnswer()
        }

        binding.firstBtn.setOnClickListener { handleOptionClick(1) }
        binding.secondBtn.setOnClickListener { handleOptionClick(2) }
        binding.thirdBtn.setOnClickListener { handleOptionClick(3) }
        binding.fourthBtn.setOnClickListener { handleOptionClick(4) }

        binding.next.setOnClickListener {
            resetGameState()
            setText()
        }

        binding.back.setOnClickListener {
            navigateToMain()
        }
    }

    private fun evaluateAnswer() {
        binding.check.visibility = View.GONE
        binding.next.visibility = View.VISIBLE

        val selectedOption = clicked
        val correctOption = correctGame2

        val options = listOf(binding.firstBtn, binding.secondBtn, binding.thirdBtn, binding.fourthBtn)

        options.forEachIndexed { index, button ->
            if (index + 1 == correctOption) {
                if (index + 1 == selectedOption) {
                    button.background = resources.getDrawable(R.drawable.rounded_button_correct_all)
                } else {
                    button.background = resources.getDrawable(R.drawable.rounded_button_correct)
                }
            } else if (index + 1 == selectedOption) {
                button.background = resources.getDrawable(R.drawable.rounded_button_incorrect)
            }
            button.isEnabled = false
        }
    }

    private fun handleOptionClick(option: Int) {
        clicked = option
        binding.firstBtn.isEnabled = false
        binding.secondBtn.isEnabled = false
        binding.thirdBtn.isEnabled = false
        binding.fourthBtn.isEnabled = false

        when (option) {
            1 -> binding.firstBtn.background = resources.getDrawable(R.drawable.rounded_button_correct_all)
            2 -> binding.secondBtn.background = resources.getDrawable(R.drawable.rounded_button_correct_all)
            3 -> binding.thirdBtn.background = resources.getDrawable(R.drawable.rounded_button_correct_all)
            4 -> binding.fourthBtn.background = resources.getDrawable(R.drawable.rounded_button_correct_all)
        }
    }

    private fun resetGameState() {
        clicked = 0
        binding.check.visibility = View.VISIBLE
        binding.next.visibility = View.GONE

        val options = listOf(binding.firstBtn, binding.secondBtn, binding.thirdBtn, binding.fourthBtn)
        options.forEach { button ->
            button.isEnabled = true
            button.background = resources.getDrawable(R.drawable.based_radius)
        }
    }

    private fun setText() {
        binding.text.text = "gardener"
        binding.trans.text = "[ 'gɑ:dnə ]"
        binding.firstBtn.text = "Гладиолус"
        binding.secondBtn.text = "Муха"
        binding.thirdBtn.text = "Садовник"
        binding.fourthBtn.text = "Собака"
    }

    private fun navigateToMain() {
        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://app/main".toUri())
            .build()
        findNavController().navigate(request)
    }
}