package com.example.geoquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
  private val questionBank = listOf(
    Question(R.string.question_australia, true),
    Question(R.string.question_oceans, true),
    Question(R.string.question_mideast, false),
    Question(R.string.question_africa, false),
    Question(R.string.question_americas, true),
    Question(R.string.question_asia, true)
  )

  var isCheater: Boolean
    get() = savedStateHandle.get(IS_CHEATER_KEY) ?: false
    set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)

  private var currentIndex: Int
    get() = savedStateHandle[CURRENT_INDEX_KEY] ?: 0
    set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

  var score: Int = 0
  var questionsLeft: Int = 0
  val currentQuestionAnswer: Boolean
    get() = questionBank[currentIndex].answer

  val currentQuestionUserAnswer: Boolean?
    get() = questionBank[currentIndex].userAnswer

  val currentQuestionText: Int
    get() = questionBank[currentIndex].textResId

  val totalQuestions: Int
    get() = questionBank.size

  fun changeQuestion(modifier: Int) {
    currentIndex = (currentIndex + modifier) % questionBank.size
    if (currentIndex < 0) {
      currentIndex = questionBank.size - 1
    }
  }

  fun updateAnswer(userAnswer: Boolean) {
    questionBank[currentIndex].userAnswer = userAnswer
  }

  fun updateScore() {
    questionsLeft = 0
    score = 0
    for (i in questionBank) {
      if (i.userAnswer == null) {
        questionsLeft++
      } else if (i.userAnswer == i.answer) {
        score++
      }
    }
  }

  fun resetQuiz() {
    for (i in questionBank) {
      i.userAnswer = null
    }
    currentIndex = 0
    isCheater = false
  }
}
