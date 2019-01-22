package com.example.dia.tests.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.dia.tests.R
import kotlinx.android.synthetic.main.activity_test.*



class TestActivity : AppCompatActivity() {
    var presenter: Presenter? = null

    private var filename = "v1.xml" // default

    private var counter: Int = 0
    private var correctCounter: Int = 0
    private var pressedButtonId: Int = 0
    private var pressed: Boolean = false
    private var ended: Boolean = false

    // SAVINGS
    private val currentQuestionStr: String = "CURRENT_QUESTION"
    private val correctCounterStr: String = "CORRECT_COUNTER"
    private val pressedButtonIdStr: String = "PRESSED_BUTTON_ID"
    private val pressedStateStr: String = "PRESSED_STATE"
    private val endedTestStr: String = "ENDED_TEST"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        filename = intent.getStringExtra("value")

        if(savedInstanceState != null)
        {
            restore(savedInstanceState)
        }
        else {
            onStartFirst()
        }

        // вешаем лиснеры
        buttonAnswer1.setOnClickListener {
            save(it.id)
            colorButtons(it.id)
        }
        buttonAnswer2.setOnClickListener {
            save(it.id)
            colorButtons(it.id)
        }
        buttonAnswer3.setOnClickListener {
            save(it.id)
            colorButtons(it.id)
        }
        buttonAnswer4.setOnClickListener {
            save(it.id)
            colorButtons(it.id)
        }
        buttonNext.setOnClickListener {
            loadNextQuestion()
        }
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putInt(currentQuestionStr, counter)
        outState!!.putInt(correctCounterStr, correctCounter)
        outState!!.putInt(pressedButtonIdStr, pressedButtonId)
        outState!!.putBoolean(pressedStateStr, pressed)
        outState!!.putBoolean(endedTestStr, ended)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        counter = savedInstanceState!!.getInt(currentQuestionStr)
        correctCounter = savedInstanceState!!.getInt(correctCounterStr)
        pressedButtonId = savedInstanceState!!.getInt(pressedButtonIdStr)
        pressed = savedInstanceState!!.getBoolean(pressedStateStr)
        ended = savedInstanceState!!.getBoolean(endedTestStr)
    }

    // click performed
    private fun save(id: Int) {
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
        when(presenter!!.itemList[counter].correct) {
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

    private fun loadQuestionsIntoButtons() {
        if((presenter!!.itemList!!.size - 1) > counter) {
            // refresh button colors
            colorClearButtons()

            textView.text = presenter!!.itemList?.get(counter)?.question
            buttonAnswer1.text = presenter!!.itemList[counter].answers[0]
            buttonAnswer2.text = presenter!!.itemList[counter].answers[1]
            buttonAnswer3.text = presenter!!.itemList[counter].answers[2]
            buttonAnswer4.text = presenter!!.itemList[counter].answers[3]
        }
        else {
            // TODO show results
            buttonAnswer1.visibility = View.INVISIBLE
            buttonAnswer2.visibility = View.INVISIBLE
            buttonAnswer3.visibility = View.INVISIBLE
            buttonAnswer4.visibility = View.INVISIBLE
            buttonNext.visibility = View.INVISIBLE

            textView.text = "Total: " +  correctCounter.toString() + " from " + presenter!!.totalQuestions.toString()
            ended = true
        }
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
        // refresh button colors
        colorClearButtons()

        //(itemList.size()-1) > counter
        //if (presenter!!.totalQuestions != currentQuestion)
        //Log.d("ssss", presenter!!.itemList!!.size.toString())
        //Log.d("ssss", counter.toString())
        if((presenter!!.itemList!!.size - 1) > counter)
        {
            counter++
            loadQuestionsIntoButtons()
        }
    }

    private fun checkAnswer(textFromButton: String): Boolean {
        // ответ правильный
        //Log.d("dddd", textFromButton) // real
        //Log.d("dddd", presenter!!.itemList[currentQuestion-1].correct)

        return if(textFromButton == presenter!!.itemList[counter].correct) {
            correctCounter++
            true
        }
        // ответ не правильный
        else {
            false
        }
    }

    private fun onStartFirst() {
        presenter = Presenter(this.applicationContext)
        presenter!!.doParse(filename)

        loadQuestionsIntoButtons()
    }

    private fun restore(bundle: Bundle?) {
        counter = bundle!!.getInt(currentQuestionStr)
        correctCounter = bundle!!.getInt(correctCounterStr)
        pressedButtonId = bundle!!.getInt(pressedButtonIdStr)
        pressed = bundle!!.getBoolean(pressedStateStr)
        ended = bundle!!.getBoolean(endedTestStr)

        presenter = Presenter(this.applicationContext)
        presenter!!.doParse(filename)

        //currentQuestion
        //loadNextQuestion()
        loadQuestionsIntoButtons()
        if(pressed) // emit click
        {
            when(pressedButtonId)
            {
                buttonAnswer1.id -> buttonAnswer1.post { buttonAnswer1.performClick() }
                buttonAnswer2.id -> buttonAnswer2.post { buttonAnswer2.performClick() }
                buttonAnswer3.id -> buttonAnswer3.post { buttonAnswer3.performClick() }
                buttonAnswer4.id -> buttonAnswer4.post { buttonAnswer4.performClick() }
            }
        }

    }

}
