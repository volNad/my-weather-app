package com.example.myweatherapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.weatherapp.databinding.ActivitySplashsScreenBinding

class SplashsScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashsScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashsScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed({
            val intent = Intent(this@SplashsScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }

    }


