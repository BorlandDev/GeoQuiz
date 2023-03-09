package com.bignerdranch.android.geouquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bignerdranch.android.geouquiz.databinding.ActivityCheatBinding

class CheatActivity : AppCompatActivity() {

    private var _binding: ActivityCheatBinding? = null
    private val binding: ActivityCheatBinding
        get() = _binding
            ?: throw IllegalStateException("MainActivity binding == null in ${lifecycle.currentState}")

    private var answerIsTrue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        binding.showAnswerButton.setOnClickListener {
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            binding.answerTextView.setText(answerText)
            setAnswerShownResult()
        }
    }

    private fun setAnswerShownResult() {
        setResult(Activity.RESULT_OK,
            Intent(this, MainActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_SHOWN, true)
            }
        )
    }

    companion object {
        private const val EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true"
        const val EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown"


        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent =
            Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
    }
}