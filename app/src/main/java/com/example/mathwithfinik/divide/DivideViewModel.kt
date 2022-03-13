package com.example.mathwithfinik.divide

import android.util.Log
import androidx.navigation.findNavController
import com.example.mathwithfinik.BaseViewModel
import com.example.mathwithfinik.R
import com.example.mathwithfinik.databinding.ExerciseFragmentBinding
import com.example.mathwithfinik.models.MathProblemModel
import kotlin.random.Random

class DivideViewModel(override val binding: ExerciseFragmentBinding) : BaseViewModel(binding) {
    override fun makeMathProblemModel(
        level: String?
    ): MathProblemModel {

        Log.i("MyLogs", "START")
        val answer: Int = Random.nextInt(2, 10)
        Log.i("MyLogs", "| 1 | ")
        val secondValue: Int = Random.nextInt(2, 10)
        Log.i("MyLogs", "| 2 | ")
        val firstValue = secondValue * answer
        Log.i("MyLogs", "| 3 | ")
        val wrongAnswers = ArrayList<Number>()
        Log.i("MyLogs", "| 4 | ")
        var max = answer * 2
        while (max < 5) {
            max++
        }
        Log.i("MyLogs", "| 5 | ")
        val min = 1
        Log.i("MyLogs", "| 6 | ")
        while (wrongAnswers.size < 3) {
            val value = Random.nextInt(min, max)
            if (value != answer && !wrongAnswers.contains(value)) {
                Log.i("MyLogs", "| 9 | ")
                wrongAnswers.add(value)
                Log.i("MyLogs", "| 10 | ")
            }
            Log.i("MyLogs", "| 11 | ")
        }
        Log.i("MyLogs", "FINISH ")

        return MathProblemModel(firstValue, secondValue, answer, wrongAnswers)
    }

    override fun actionBackToMainScreen() {
        binding.root.findNavController()
            .navigate(R.id.action_divideFragment_to_mainScreenFragment)
    }
}