package com.example.mathwithfinik.models

data class MathExerciseModel(
    val firstValue:Int,
    val secondValue:Int,
    val answerValue: Int,
    //three numbers of wrong answers
    var wrongAnswers: ArrayList<Number>
)
