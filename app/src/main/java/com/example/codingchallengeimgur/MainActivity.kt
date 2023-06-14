package com.example.codingchallengeimgur

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.codingchallengeimgur.databinding.ActivityMainBinding
import com.example.codingchallengeimgur.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Thread {
            Thread.sleep(2000)
            startActivity(Intent(this, HomeActivity::class.java)).apply {
                finish()
            }
        }.start()
    }
}