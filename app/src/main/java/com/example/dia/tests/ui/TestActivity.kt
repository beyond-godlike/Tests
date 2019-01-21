package com.example.dia.tests.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.dia.tests.R
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {
    var presenter: Presenter? = null

    private var filename = "v1.xml" // default

    private var currentQuestion: Int = 0
    private var correctCounter: Int = 0
    private var pressedButtonId: Int = 0
    private var pressed: Boolean = false

    // SAVINGS
    private val CURRENT_QUESTION: String = "CURRENT_QUESTION"
    private val CORRECT_COUNTER: String = "CORRECT_COUNTER"
    private val PRESSED_BUTTON_ID: String = "PRESSED_BUTTON_ID"
    private val PRESSED_STATE: String = "PRESSED_STATE"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        filename = intent.getStringExtra("value")

        if(savedInstanceState != null)
        {
            currentQuestion = savedInstanceState!!.getInt(CURRENT_QUESTION)
            correctCounter = savedInstanceState!!.getInt(CORRECT_COUNTER)
            pressedButtonId = savedInstanceState!!.getInt(PRESSED_BUTTON_ID)
            pressed = savedInstanceState!!.getBoolean(PRESSED_STATE)
            restore()
        }
        else {
            onStartFirst()
        }

        // вешаем лиснеры
        buttonAnswer1.setOnClickListener {
            save(it.id, buttonAnswer1.text.toString())
            colorButtons(it.id)
        }
        buttonAnswer2.setOnClickListener {
            save(it.id, buttonAnswer2.text.toString())
            colorButtons(it.id)
        }
        buttonAnswer3.setOnClickListener {
            save(it.id, buttonAnswer3.text.toString())
            colorButtons(it.id)
        }
        buttonAnswer4.setOnClickListener {
            save(it.id, buttonAnswer4.text.toString())
            colorButtons(it.id)
        }
        buttonNext.setOnClickListener {
            loadNextQuestion()
        }
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putInt(CURRENT_QUESTION, currentQuestion)
        outState!!.putInt(CORRECT_COUNTER, correctCounter)
        //outState!!.putString("CURRENT_ANSWER", currentAnswer) //?
        outState!!.putInt(PRESSED_BUTTON_ID, pressedButtonId)
        outState!!.putBoolean(PRESSED_STATE, pressed)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        currentQuestion = savedInstanceState!!.getInt(CURRENT_QUESTION)
        correctCounter = savedInstanceState!!.getInt(CORRECT_COUNTER)
        pressedButtonId = savedInstanceState!!.getInt(PRESSED_BUTTON_ID)
        pressed = savedInstanceState!!.getBoolean(PRESSED_STATE)
    }

    // click performed
    private fun save(id: Int, textFromBtn: String) {
        pressedButtonId = id
        pressed = true

        // lock buttons
        buttonAnswer1.isClickable = false
        buttonAnswer2.isClickable = false
        buttonAnswer3.isClickable = false
        buttonAnswer4.isClickable = false
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
        //currentQuestion-1
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
        // unlock buttons if was pressed
        if(pressed) {
            buttonAnswer1.isClickable = true
            buttonAnswer2.isClickable = true
            buttonAnswer3.isClickable = true
            buttonAnswer4.isClickable = true
        }
        // was not pressed but we should load questions
        else {

        }

        pressed = false
        if (presenter!!.totalQuestions != currentQuestion)
        {
            // refresh button colors
            colorClearButtons()
            textView.text = presenter!!.itemList?.get(currentQuestion)?.question
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
        //Log.d("dddd", presenter!!.itemList[currentQuestion-1].correct)

        if(textFromButton == presenter!!.itemList.get(currentQuestion-1).correct) {
            correctCounter++
            return true
        }
        // ответ не правильный
        else {
            return false
        }
    }

    private fun onStartFirst() {
        presenter = Presenter(this.applicationContext)
        presenter!!.doParse(filename)

        loadNextQuestion()
    }

    private fun restore() {
        presenter = Presenter(this.applicationContext)
        presenter!!.doParse(filename)

        //currentQuestion
        loadNextQuestion()
        if(pressed)
        {
            colorButtons(pressedButtonId)
        }

    }

}