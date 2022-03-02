package com.example.mathwithfinik

import com.example.mathwithfinik.databinding.ExerciseFragmentBinding
import com.example.mathwithfinik.multiply.MathProblemModel
import kotlin.random.Random

class DivideViewModel : BaseViewModel() {
    override fun makeMathProblemModel(): MathProblemModel {
        val answer: Int = Random.nextInt(1, 10)
        val secondValue: Int = Random.nextInt(1, 10)
        val firstValue = secondValue * answer;
        val wrongAnswers = ArrayList<Int>()
        val max = answer + answer
        val min = 1
        while (wrongAnswers.size < 3) {
            val value = Random.nextInt(min, max)
            if (value != answer && !wrongAnswers.contains(value)) {
                wrongAnswers.add(value)
            }
        }
        return MathProblemModel(firstValue, secondValue, answer, wrongAnswers)
    }

    override fun generateNewExercise(binding: ExerciseFragmentBinding) {
        binding.exercise.symbol.text = "x"
        super.generateNewExercise(binding)
    }
}