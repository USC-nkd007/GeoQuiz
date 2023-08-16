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

  private val questionBank = listOf(
    Question(R.string.question_australia, true),
    Question(R.string.question_oceans, true),
    Question(R.string.question_mideast, false),
    Question(R.string.question_africa, false),
    Question(R.string.question_americas, true),
    Question(R.string.question_asia, true)
  )

  private var currentIndex: Int = 0

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
      changeQuestion(1)
    }

    binding.prevButton.setOnClickListener {
      changeQuestion(-1)
    }

    binding.resetButton.setOnClickListener {
      resetQuiz()
    }

    binding.questionTextView.setOnClickListener {
      changeQuestion(1)
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

  private fun changeQuestion(modifier: Int) {
    currentIndex = (currentIndex + modifier) % questionBank.size
    if (currentIndex < 0) {
      currentIndex = questionBank.size - 1
    }
    updateQuestion()
    if (questionBank[currentIndex].userAnswer == null) {
      toggleAnswerButtons(true)
    }
  }

  private fun updateQuestion() {
    val questionTextResId = questionBank[currentIndex].textResId
    binding.questionTextView.setText(questionTextResId)
  }

  private fun checkAnswer(userAnswer: Boolean, contextView: View) {
    val correctAnswer: Boolean = questionBank[currentIndex].answer
    questionBank[currentIndex].userAnswer = userAnswer

    val messageResId: Int = if (userAnswer == correctAnswer) {
      R.string.correct_toast
    } else {
      R.string.incorrect_toast
    }

    Snackbar.make(contextView, messageResId, Snackbar.LENGTH_SHORT).show()
    toggleAnswerButtons(false)
    checkScore()
  }

  private fun checkScore() {
    var score = 0
    for (i in questionBank) {
      if (i.userAnswer == null) {
        return
      } else if (i.userAnswer == i.answer) {
        score++
      }
    }
    toggleQuestionButtons(false)

    val totalQuestions = questionBank.size
    val percentage = (score * 100) / totalQuestions
    val scoreText = getString(R.string.score_message, score, totalQuestions, percentage)
    binding.questionTextView.text = scoreText
  }

  private fun resetQuiz() {
    for (i in questionBank) {
      i.userAnswer = null
    }
    toggleQuestionButtons(true)
    toggleAnswerButtons(true)
    currentIndex = 0
    updateQuestion()
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
