package com.example.dia.tests.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.dia.tests.R
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {
    var presenter: Presenter? = null

    private val filename = "v1.xml" // default

    private var currentQuestion: Int = 0
    private var correctCounter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        onStartFirst()

        // вешаем лиснеры
        buttonAnswer1.setOnClickListener {
            colorButtons(it.id)
        }
        buttonAnswer2.setOnClickListener {
            colorButtons(it.id)
        }
        buttonAnswer3.setOnClickListener {
            colorButtons(it.id)
        }
        buttonAnswer4.setOnClickListener {
            colorButtons(it.id)
        }
        buttonNext.setOnClickListener {
            loadNextQuestion()
        }
    }
    private fun colorButtons(id: Int)
    {
        when(id) {
           buttonAnswer1.id -> {
               if(checkAnswer(buttonAnswer1.text.toString()))
                   buttonAnswer1.setBackgroundResource(R.drawable.button_style_try_correct)
               else
                   buttonAnswer1.setBackgroundResource(R.drawable.button_style_try_wrong)
           }

            buttonAnswer2.id -> {
                if(checkAnswer(buttonAnswer2.text.toString()))
                    buttonAnswer2.setBackgroundResource(R.drawable.button_style_try_correct)
                else
                    buttonAnswer2.setBackgroundResource(R.drawable.button_style_try_wrong)
            }

            buttonAnswer3.id -> {
                if(checkAnswer(buttonAnswer3.text.toString()))
                    buttonAnswer3.setBackgroundResource(R.drawable.button_style_try_correct)
                else
                    buttonAnswer3.setBackgroundResource(R.drawable.button_style_try_wrong)
            }

            buttonAnswer4.id -> {
                if(checkAnswer(buttonAnswer4.text.toString()))
                    buttonAnswer4.setBackgroundResource(R.drawable.button_style_try_correct)
                else
                    buttonAnswer4.setBackgroundResource(R.drawable.button_style_try_wrong)
            }
        }

        // теперь нужно покрасить правильный ответ
        // хотя эту кнопку мы не нажимали
        when(presenter!!.itemList[currentQuestion-1].correct) {
            buttonAnswer1.text.toString() -> buttonAnswer1.setBackgroundResource(R.drawable.button_style_try_correct)
            buttonAnswer2.text.toString() -> buttonAnswer2.setBackgroundResource(R.drawable.button_style_try_correct)
            buttonAnswer3.text.toString() -> buttonAnswer3.setBackgroundResource(R.drawable.button_style_try_correct)
            buttonAnswer4.text.toString() -> buttonAnswer4.setBackgroundResource(R.drawable.button_style_try_correct)

        }

    }

    private fun colorClearButtons(){
        buttonAnswer1.setBackgroundResource(R.drawable.button_style_try)
        buttonAnswer2.setBackgroundResource(R.drawable.button_style_try)
        buttonAnswer3.setBackgroundResource(R.drawable.button_style_try)
        buttonAnswer4.setBackgroundResource(R.drawable.button_style_try)

    }

    private fun loadNextQuestion() {
        if (presenter!!.totalQuestions != currentQuestion)
        {
            // refresh button colors
            colorClearButtons()
            textView.text = presenter!!.itemList.get(currentQuestion).question
            buttonAnswer1.text = presenter!!.itemList.get(currentQuestion).answers.get(0)
            buttonAnswer2.text = presenter!!.itemList.get(currentQuestion).answers.get(1)
            buttonAnswer3.text = presenter!!.itemList.get(currentQuestion).answers.get(2)
            buttonAnswer4.text = presenter!!.itemList.get(currentQuestion).answers.get(3)

            currentQuestion++
        }
        else {
            // TODO show results
            buttonAnswer1.visibility = View.INVISIBLE
            buttonAnswer2.visibility = View.INVISIBLE
            buttonAnswer3.visibility = View.INVISIBLE
            buttonAnswer4.visibility = View.INVISIBLE
            buttonNext.visibility = View.INVISIBLE

            textView.text = "ToTal: " +  correctCounter.toString() + " from " + presenter!!.totalQuestions.toString()
        }
    }

    private fun checkAnswer(textFromButton: String): Boolean {
        // ответ правильный
        Log.d("dddd", textFromButton) // real
        Log.d("dddd", presenter!!.itemList[currentQuestion-1].correct)

        if(textFromButton == presenter!!.itemList.get(currentQuestion-1).correct) {
            correctCounter++
            return true
        }
        // ответ не правильный
        else {
            return false
        }
    }

    fun onStartFirst() {
        presenter = Presenter(this.applicationContext)
        presenter!!.doParse(filename)

        loadNextQuestion()
    }

}