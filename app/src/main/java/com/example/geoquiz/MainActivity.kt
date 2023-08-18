package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.example.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding


  private val quizViewModel: QuizViewModel by viewModels()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.d(TAG, "onCreate(Bundle?) called")
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

    binding.resetButton.isEnabled = false

    binding.trueButton.setOnClickListener { view: View ->
      checkAnswer(true, view)
    }

    binding.falseButton.setOnClickListener { view: View ->
      checkAnswer(false, view)
    }

    binding.nextButton.setOnClickListener {
      quizViewModel.changeQuestion(1)
      updateQuestion()
    }

    binding.prevButton.setOnClickListener {
      quizViewModel.changeQuestion(-1)
      updateQuestion()
    }

    binding.resetButton.setOnClickListener {
      quizViewModel.resetQuiz()
      toggleQuestionButtons(true)
      toggleAnswerButtons(true)
      updateQuestion()
    }

    binding.questionTextView.setOnClickListener {
      quizViewModel.changeQuestion(1)
      updateQuestion()
    }

    updateQuestion()
  }

  override fun onStart() {
    super.onStart()
    Log.d(TAG, "onStart() called")
  }

  override fun onResume() {
    super.onResume()
    Log.d(TAG, "onResume() called")
  }

  override fun onPause() {
    super.onPause()
    Log.d(TAG, "onPause() called")
  }

  override fun onStop() {
    super.onStop()
    Log.d(TAG, "onStop() called")
  }

  override fun onDestroy() {
    super.onDestroy()
    Log.d(TAG, "onDestroy() called")
  }

  private fun updateQuestion() {
    val questionTextResId = quizViewModel.currentQuestionText
    binding.questionTextView.setText(questionTextResId)
    if (quizViewModel.currentQuestionUserAnswer == null) {
      toggleAnswerButtons(true)
    } else {
      toggleAnswerButtons(false)
    }
    quizViewModel.updateScore()
    if (quizViewModel.questionsLeft > 0) {
      toggleQuestionButtons(true)
    } else {
      toggleQuestionButtons(false)
      printScore()
    }
  }

  private fun checkAnswer(userAnswer: Boolean, contextView: View) {
    quizViewModel.updateAnswer(userAnswer)

    val messageResId: Int = if (userAnswer == quizViewModel.currentQuestionAnswer) {
      R.string.correct_toast
    } else {
      R.string.incorrect_toast
    }

    Snackbar.make(contextView, messageResId, Snackbar.LENGTH_SHORT).show()
    toggleAnswerButtons(false)
    updateQuestion()
  }

  private fun printScore() {
    toggleQuestionButtons(false)

    val percentage = (quizViewModel.score * 100) / quizViewModel.totalQuestions
    val scoreText = getString(R.string.score_message, quizViewModel.score, quizViewModel.totalQuestions, percentage)
    binding.questionTextView.text = scoreText
  }

  private fun toggleQuestionButtons(state: Boolean) {
    binding.nextButton.isEnabled = state
    binding.prevButton.isEnabled = state
    binding.resetButton.isEnabled = !state
  }

  private fun toggleAnswerButtons(state: Boolean) {
    binding.trueButton.isEnabled = state
    binding.falseButton.isEnabled = state
  }
}
