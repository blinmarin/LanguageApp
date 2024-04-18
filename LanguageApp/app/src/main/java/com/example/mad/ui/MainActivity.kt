package com.example.mad.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.mad.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.example.mad.databinding.ActivityMainBinding
import com.example.mad.viewInflateBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewInflateBinding(ActivityMainBinding::inflate)
    private val navController: NavController by lazy {
        findNavController(R.id.mainHostFragment)
    }
    private var uiStateJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupUI()
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

    override fun onStop() {
        uiStateJob?.cancel()
        super.onStop()
    }

    override fun onBackPressed() {
        if (isTaskRoot
            && supportFragmentManager.primaryNavigationFragment?.childFragmentManager?.backStackEntryCount == 0
            && supportFragmentManager.backStackEntryCount == 0
        ) {
            finishAfterTransition()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        if (isTaskRoot && isFinishing) {
            finishAfterTransition()
        }
        super.onDestroy()
    }

    private fun setupUI() {
        lifecycleScope.launch {
        }
    }

}