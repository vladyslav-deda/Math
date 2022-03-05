package com.example.mathwithfinik.multiply

import com.example.mathwithfinik.BaseViewModel
import com.example.mathwithfinik.models.MathProblemModel
import com.example.mathwithfinik.databinding.ExerciseFragmentBinding
import kotlin.random.Random

class MultiplyViewModel() : BaseViewModel() {

    override fun makeMathProblemModel(
        level: Char?,
        binding: ExerciseFragmentBinding
    ): MathProblemModel {
        val firstValue: Int = Random.nextInt(1, 10)
        val secondValue: Int = Random.nextInt(1, 10)
        val answer = firstValue * secondValue;
        val wrongAnswers = ArrayList<Int>()
        val max = answer + answer
        val min = 1
        while (wrongAnswers.size < 3) {
            val value = Random.nextInt(min, max)
            if (value != answer && !wrongAnswers.contains(value)) {
                wrongAnswers.add(value)
            }
        }
        binding.exercise.symbol.text = "*"
        return MathProblemModel(firstValue, secondValue, answer, wrongAnswers)
    }
}