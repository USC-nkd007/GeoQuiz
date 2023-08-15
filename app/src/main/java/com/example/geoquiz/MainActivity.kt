package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
//import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

  private lateinit var trueButton: Button
  private lateinit var falseButton: Button
  private lateinit var nextButton: ImageButton
  private lateinit var prevButton: ImageButton
  private lateinit var resetButton: Button
  private lateinit var questionTextView: TextView

  private val questionBank = listOf(
    Question(R.string.question_australia, true),
    Question(R.string.question_oceans, true),
    Question(R.string.question_mideast, false),
    Question(R.string.question_africa, false),
    Question(R.string.question_americas, true),
    Question(R.string.question_asia, true)
  )

  private var currentIndex: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.d(TAG, "onCreate(Bundle?) called")
    setContentView(R.layout.activity_main)

    trueButton = findViewById(R.id.true_button)
    falseButton = findViewById(R.id.false_button)
    nextButton = findViewById(R.id.next_button)
    prevButton = findViewById(R.id.prev_button)
    resetButton = findViewById(R.id.reset_button)
    questionTextView = findViewById(R.id.question_text_view)

    resetButton.isEnabled = false

    trueButton.setOnClickListener { view: View ->
      checkAnswer(true, view)
    }

    falseButton.setOnClickListener { view: View ->
      checkAnswer(false, view)
    }

    nextButton.setOnClickListener {
      changeQuestion(1)
    }

    prevButton.setOnClickListener {
      changeQuestion(-1)
    }

    resetButton.setOnClickListener {
      resetQuiz()
    }

    questionTextView.setOnClickListener {
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
    questionTextView.setText(questionTextResId)
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
    questionTextView.text = scoreText
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
    nextButton.isEnabled = state
    prevButton.isEnabled = state
    resetButton.isEnabled = !state
  }

  private fun toggleAnswerButtons(state: Boolean) {
    trueButton.isEnabled = state
    falseButton.isEnabled = state
  }
}
