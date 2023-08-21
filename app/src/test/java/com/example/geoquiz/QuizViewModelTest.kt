package com.example.geoquiz

import androidx.lifecycle.SavedStateHandle
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class QuizViewModelTest {
  @Test
  fun providesExpectedQuestionText() {
    val savedStateHandle = SavedStateHandle()
    val quizViewModel = QuizViewModel(savedStateHandle)
    assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
  }

  @Test
  fun wrapsAroundQuestionBank() {
    val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 5))
    val quizViewModel = QuizViewModel(savedStateHandle)
    assertEquals(R.string.question_asia, quizViewModel.currentQuestionText)
    quizViewModel.changeQuestion(1)
    assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
  }

  @Test
  fun currentQuestionAnswerIsFalse() {
    val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 1))
    val quizViewModel = QuizViewModel(savedStateHandle)
    assertTrue(quizViewModel.currentQuestionAnswer)
  }

  @Test
  fun currentQuestionAnswerIsTrue() {
    val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 2))
    val quizViewModel = QuizViewModel(savedStateHandle)
    assertFalse(quizViewModel.currentQuestionAnswer)
  }
}
