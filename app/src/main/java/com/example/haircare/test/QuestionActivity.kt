package com.example.haircare.test

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.haircare.R
import kotlinx.android.synthetic.main.activity_question.*

class QuestionActivity : AppCompatActivity() {

    val questionList = CreatingQuestion.getQuestion() // getQuestion to funkcja któą sama napisałąm
    var currentPosition = 1
    var choosedAnswer: ArrayList<Int> = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        setQuestion()

        answer_1.setOnClickListener() {
            choosedAnswer.add(1)
            currentPosition++
            checkIfNewQuestionOrEnd()
        }
        answer_2.setOnClickListener() {
            choosedAnswer.add(2)
            currentPosition++
            checkIfNewQuestionOrEnd()
        }
        answer_3.setOnClickListener() {
            choosedAnswer.add(3)
            currentPosition++
            checkIfNewQuestionOrEnd()
        }
        answer_4.setOnClickListener() {
            choosedAnswer.add(4)
            currentPosition++
            checkIfNewQuestionOrEnd()

        }


    }


    fun setQuestion() {
        val question: SingleQuestion = questionList[currentPosition - 1]
        question_text.setText(question.question)
        answer_1.text = question.answer
        answer_2.text = question.answer2
        answer_3.text = question.answer3
        answer_4.text = question.answer4
        progressBar.progress = currentPosition
        progress_bar_text.setText("$currentPosition/" + "${progressBar.max}")

    }

    fun checkIfNewQuestionOrEnd() {
        if (currentPosition <= questionList.size) setQuestion()
        else {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra(CreatingQuestion.HAIR_TYPE, choosedAnswer)
            println(choosedAnswer)
            startActivity(intent)
            finish()
        }
    }
}
