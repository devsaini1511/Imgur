package com.example.codingchallengeimgur.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.codingchallengeimgur.R
import com.example.codingchallengeimgur.databinding.ActivityHomeBinding
import com.example.codingchallengeimgur.ui.common.extension.ConnectionState
import com.example.codingchallengeimgur.ui.common.extension.ConnectionState.Unavailable
import com.example.codingchallengeimgur.ui.common.extension.observeConnectivityAsFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {


    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHost.id) as NavHostFragment

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        navController = navHostFragment.navController

        observeConnectivityAsFlow()
            .distinctUntilChanged()
            .flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    ConnectionState.Available -> {
                        binding.isAvailable = true
                        delay(500)
                        binding.networkState.isVisible = false

                    }
                    Unavailable -> {
                        binding.isAvailable = false
                        binding.networkState.isVisible = true
                    }
                }
            }.launchIn(lifecycleScope)
    }
}