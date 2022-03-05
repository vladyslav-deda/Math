package com.example.mathwithfinik.models

data class MathProblemModel(
    val firstValue:Int,
    val secondValue:Int,
    val answerValue: Int,
    var wrongAnswers: List<Int>
)
