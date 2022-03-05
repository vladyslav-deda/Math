package com.example.mathwithfinik.plus_minus_screen

import com.example.mathwithfinik.BaseViewModel
import com.example.mathwithfinik.Constants
import com.example.mathwithfinik.databinding.ExerciseFragmentBinding
import com.example.mathwithfinik.models.MathProblemModel
import kotlin.random.Random

class PlusMinusViewModel : BaseViewModel() {
    private var firstValue = 0
    private var secondValue = 0
    private var answer = 0
    private val wrongAnswers = ArrayList<Int>()
    private var max = 0
    override fun makeMathProblemModel(level: Char?, binding: ExerciseFragmentBinding): MathProblemModel {
        when (level) {
            Constants.EASY_CHAR -> {
                firstValue = Random.nextInt(1, 10)
                secondValue = Random.nextInt(1, 10)
            }
            Constants.MEDIUM_CHAR -> {
                firstValue = Random.nextInt(1, 100)
                secondValue = Random.nextInt(1, 100)
            }
            Constants.HARD_CHAR -> {
                firstValue = Random.nextInt(1, 1000)
                secondValue = Random.nextInt(1, 1000)
            }
            else -> {
                firstValue = 0
                secondValue = 0
            }
        }
        if (Random.nextBoolean()) {
            answer = firstValue + secondValue
            max = answer + firstValue
            binding.exercise.symbol.text = "+"
        } else {
            answer = firstValue - secondValue
            max = firstValue + secondValue
            binding.exercise.symbol.text = "-"
        }
        val min = 1
        while (wrongAnswers.size < 3) {
            val value = Random.nextInt(min, max)
            if (value != answer && !wrongAnswers.contains(value)) {
                wrongAnswers.add(value)
            }
        }
        return MathProblemModel(firstValue, secondValue, answer, wrongAnswers)
    }

    override fun generateNewExercise(binding: ExerciseFragmentBinding, level: Char?) {

        super.generateNewExercise(binding, level)
    }
}