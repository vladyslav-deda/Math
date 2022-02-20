package com.example.mathwithfinik.multiply

data class MathProblemModel(
    val firstValue:Int,
    val secondValue:Int,
    val answerValue: Int,
    var wrongAnswers: List<Int>
)
