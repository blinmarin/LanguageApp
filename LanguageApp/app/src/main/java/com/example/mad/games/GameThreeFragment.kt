package com.example.mad.games

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.mad.R
import com.example.mad.databinding.GameThreeFragmentBinding
import com.example.mad.viewBinding
import java.util.Locale

class GameThreeFragment : Fragment(R.layout.game_three_fragment) {

    private val binding by viewBinding(GameThreeFragmentBinding::bind)
    private val RECORD_AUDIO_REQUEST_CODE = 101
    private lateinit var speechRecognizer: SpeechRecognizer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPermissions()
        setupSpeechRecognizer()
        setupListeners()
        downloadText()
    }

    private fun setupPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.RECORD_AUDIO),
                RECORD_AUDIO_REQUEST_CODE
            )
        }
    }

    private fun setupSpeechRecognizer() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(requireContext())
        speechRecognizer.setRecognitionListener(recognitionListener)
    }

    private fun setupListeners() {
        binding.back.setOnClickListener { navigateToMain() }
        binding.checkSpeechBtn.setOnClickListener {
            binding.animalText.setText("")
            startRecording()
        }
        binding.micro.setOnClickListener { stopRecording() }
        binding.next.setOnClickListener {
            downloadText()
            binding.next.visibility = View.GONE
            binding.checkSpeechBtn.visibility = View.VISIBLE
            binding.thirdAfter.visibility = View.GONE
            binding.animalText.setText("")
        }
    }

    private fun downloadText() {
        binding.text.text = "cucumber"
        binding.trans.text = "[ 'kju:kʌmbə ]"
    }

    private fun startRecording() {
        binding.micro.visibility = View.VISIBLE
        binding.checkSpeechBtn.visibility = View.GONE
        binding.thirdAfter.visibility = View.VISIBLE

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH)
        speechRecognizer.startListening(intent)
    }

    private fun stopRecording() {
        speechRecognizer.stopListening()
        binding.micro.clearAnimation()
    }

    private val recognitionListener = object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {}
        override fun onBeginningOfSpeech() {}
        override fun onRmsChanged(rmsdB: Float) {}
        override fun onBufferReceived(buffer: ByteArray?) {}
        override fun onEndOfSpeech() {}
        override fun onError(error: Int) {}
        override fun onResults(results: Bundle?) {
            if (results != null) {
                val matches: ArrayList<String>? =
                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val recognizedText = matches?.getOrNull(0) ?: ""
                updateTextView(recognizedText)
            }
        }
        override fun onPartialResults(partialResults: Bundle?) {}
        override fun onEvent(eventType: Int, params: Bundle?) {}
    }

    private fun updateTextView(text: String) {
        binding.animalText.setText(text)
        val isCorrect = binding.text.text.toString().equals(text, ignoreCase = true)
        val textColor = if (isCorrect) R.color.correct else R.color.error
        binding.animalText.setTextColor(ContextCompat.getColor(requireContext(), textColor))
        binding.next.visibility = if (isCorrect) View.VISIBLE else View.GONE
        binding.checkSpeechBtn.visibility = if (isCorrect) View.GONE else View.VISIBLE
        binding.micro.visibility = View.GONE
    }

    private fun navigateToMain() {
        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://app/main".toUri())
            .build()
        findNavController().navigate(request)
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer.destroy()
    }
}