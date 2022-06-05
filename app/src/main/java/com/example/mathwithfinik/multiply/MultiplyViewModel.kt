package com.example.mathwithfinik.multiply

import androidx.navigation.findNavController
import com.example.mathwithfinik.BaseViewModel
import com.example.mathwithfinik.R
import com.example.mathwithfinik.models.MathExerciseModel
import com.example.mathwithfinik.databinding.ExerciseFragmentBinding
import kotlin.random.Random

class MultiplyViewModel(override val binding: ExerciseFragmentBinding) : BaseViewModel(binding) {

    override fun makeMathExerciseModel(
        level: String?
    ): MathExerciseModel {
        val firstValue: Int = Random.nextInt(1, 10)
        val secondValue: Int = Random.nextInt(1, 10)
        val answer = firstValue * secondValue;
        val wrongAnswers = ArrayList<Number>()
        var max = answer + answer
        while (max < 5) {
            max += answer
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

    override fun actionBackToMainScreen() {
        binding.root.findNavController()
            .navigate(R.id.action_multiplyFragment_to_mainScreenFragment)
    }
}