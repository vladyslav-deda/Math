package com.example.presentation.base.model

data class MathematicalEquation(
    val firstValue: Int,
    val secondValue: Int,
    val answerValue: Int,
    //three numbers of wrong answers
    val wrongAnswers: ArrayList<Int>,
    val equationSign: String
)