package com.bignerdranch.android.geouquiz

import android.app.Activity
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.geouquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding
            ?: throw IllegalStateException("MainActivity binding == null in ${lifecycle.currentState}")

    private val viewModel: QuizViewModel by viewModels()
    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            viewModel.isCheater =
                it.data?.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        updateQuestion()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        blurCheatBtn()
    }

    private fun setListeners() {
        binding.run {
            trueButton.setOnClickListener {
                checkAnswer(true)
            }

            falseButton.setOnClickListener {
                checkAnswer(false)
            }

            nextButton.setOnClickListener {
                viewModel.moveToNext()
                updateQuestion()
            }

            cheatButton.setOnClickListener {
                cheatLauncher.launch(
                    CheatActivity.newIntent(
                        this@MainActivity,
                        viewModel.currentQuestionAnswer
                    )
                )
            }
        }
    }

    private fun updateQuestion() {
        val questionTextResId = viewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = viewModel.currentQuestionAnswer
        val messageResId = when {
            viewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun blurCheatBtn() {
        val effect = RenderEffect.createBlurEffect(
            10.0f,
            10.0f,
            Shader.TileMode.CLAMP
        )
        binding.cheatButton.setRenderEffect(effect)
    }
}