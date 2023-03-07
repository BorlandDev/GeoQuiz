package com.bignerdranch.android.geouquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bignerdranch.android.geouquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding
            ?: throw IllegalStateException("MainActivity binding == null in ${lifecycle.currentState}")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setListeners()
    }

    private fun setListeners() {
        binding.run {
            trueButton.setOnClickListener {
                Toast.makeText(this@MainActivity, R.string.correct_toast, Toast.LENGTH_SHORT)
                    .show()
            }

            falseButton.setOnClickListener {
                Toast.makeText(this@MainActivity, R.string.incorrect_toast, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}