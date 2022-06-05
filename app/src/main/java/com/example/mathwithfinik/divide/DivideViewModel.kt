package com.example.mathwithfinik.divide

import androidx.navigation.findNavController
import com.example.mathwithfinik.BaseViewModel
import com.example.mathwithfinik.R
import com.example.mathwithfinik.databinding.ExerciseFragmentBinding
import com.example.mathwithfinik.models.MathExerciseModel
import kotlin.random.Random

class DivideViewModel(override val binding: ExerciseFragmentBinding) : BaseViewModel(binding) {

    override fun makeMathExerciseModel(
        level: String?
    ): MathExerciseModel {
        val answer: Int = Random.nextInt(2, 10)
        val secondValue: Int = Random.nextInt(2, 10)
        val firstValue = secondValue * answer
        val wrongAnswers = ArrayList<Number>()
        var max = answer * 2
        while (max < 5) {
            max++
        }
        val min = 1
        while (wrongAnswers.size < 3) {
            val value = Random.nextInt(min, max)
            if (value != answer && !wrongAnswers.contains(value)) {
                wrongAnswers.add(value)
            }
        }
        return MathExerciseModel(firstValue, secondValue, answer, wrongAnswers)
    }

    override fun actionBackToMainScreen() = binding.root.findNavController()
        .navigate(R.id.action_divideFragment_to_mainScreenFragment)

}