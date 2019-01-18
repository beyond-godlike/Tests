package com.example.dia.tests.data

data class Item (var question: String? = null,
                 var answers: ArrayList<String>,
                 var correct: String? = null ) {

    fun addAnswers(answer: String) {
        answers.add(answer)
    }
}