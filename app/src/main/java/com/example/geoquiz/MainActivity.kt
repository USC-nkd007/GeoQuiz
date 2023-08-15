package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
//import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    private var currentIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)


        trueButton.setOnClickListener {view: View ->
            checkAnswer(true, view)
        }

        falseButton.setOnClickListener {view: View ->
            checkAnswer(false, view)
        }

        nextButton.setOnClickListener {
            changeQuestion(1)
        }

        prevButton.setOnClickListener {
            changeQuestion(-1)
        }

        questionTextView.setOnClickListener {
            changeQuestion(1)
        }

        updateQuestion()
    }

    private fun changeQuestion(modifier: Int) {
        currentIndex = (currentIndex + modifier) % questionBank.size
        if (currentIndex < 0) {
          currentIndex = questionBank.size - 1
        }
        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean, contextView: View) {
        val correctAnswer: Boolean = questionBank[currentIndex].answer

        val messageResId: Int = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

            Snackbar.make(contextView, messageResId, Snackbar.LENGTH_SHORT).show()
//        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}
