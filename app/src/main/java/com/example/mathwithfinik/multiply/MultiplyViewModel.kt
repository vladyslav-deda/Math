package com.example.mathwithfinik.multiply

import android.annotation.SuppressLint
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.mathwithfinik.BaseViewModel
import com.example.mathwithfinik.databinding.ExerciseFragmentBinding
import kotlin.random.Random

class MultiplyViewModel() : BaseViewModel() {

    override fun makeMathProblemModel(): MathProblemModel {
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
        return MathProblemModel(firstValue, secondValue, answer, wrongAnswers)
    }
    override fun generateNewExercise(binding: ExerciseFragmentBinding) {
        binding.exercise.symbol.text = "*"
        super.generateNewExercise(binding)
    }

}