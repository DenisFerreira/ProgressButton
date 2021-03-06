package com.denisferreira.progressbutton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.denisferreira.progressbutton.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressButton.setOnClickListener{
            binding.progressButton.setLoading()
            lifecycleScope.launch {
                delay(3_000)
                binding.progressButton.setNormal()
            }
        }
    }
}