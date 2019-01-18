package com.example.dia.tests.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.dia.tests.R
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {
    var presenter: Presenter? = null

    private val filename = "j.xml" // default

    private var currentQuestion: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        onStartFirst()

        buttonNext.setOnClickListener {
            loadNextQuestion()
        }
    }

    private fun loadNextQuestion() {
        if (presenter!!.totalQuestions != currentQuestion)
        {
            textView.text = presenter!!.itemList.get(currentQuestion).question
            buttonAnswer1.text = presenter!!.itemList.get(currentQuestion).answers.get(0)
            buttonAnswer2.text = presenter!!.itemList.get(currentQuestion).answers.get(1)
            buttonAnswer3.text = presenter!!.itemList.get(currentQuestion).answers.get(2)
            buttonAnswer4.text = presenter!!.itemList.get(currentQuestion).answers.get(3)

            currentQuestion++
        }
        else {
            // TODO show results
        }
    }

    fun onStartFirst() {
        presenter = Presenter(this.applicationContext)
        presenter!!.doParse(filename)

        loadNextQuestion()
    }

}