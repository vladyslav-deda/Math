package com.example.mathwithfinik

import android.annotation.SuppressLint
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathwithfinik.databinding.ExerciseFragmentBinding
import com.example.mathwithfinik.multiply.MathProblemModel
import kotlinx.coroutines.launch
import kotlin.random.Random

class DivideViewModel : ViewModel() {
    var score = 0

    private fun makeMathProblemModel(): MathProblemModel {
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

    @SuppressLint("SetTextI18n")
    fun generateNewExercise(binding: ExerciseFragmentBinding) {
        val mathProblem = makeMathProblemModel()
        val indexOfTrueAnswer: Int = Random.nextInt(0, 3)
        val arrayOfButtons = ArrayList<Button>()
        binding.tvScore.text = "Твій рахунок: $score"
        arrayOfButtons.apply {
            add(binding.setOfFourButtons.button1)
            add(binding.setOfFourButtons.button2)
            add(binding.setOfFourButtons.button3)
            add(binding.setOfFourButtons.button4)
            get(indexOfTrueAnswer).apply {
                text = mathProblem.answerValue.toString()
                setOnClickListener {
                    generateNewExercise(binding)

                    score++
                    binding.tvScore.text = "Твій рахунок: $score"
                }
            }
            var counter = 0
            for (i in 0..3) {
                if (indexOfTrueAnswer != i) {
                    arrayOfButtons[i].apply {
                        text = mathProblem.wrongAnswers[counter].toString()
                        setOnClickListener {
                            Toast.makeText(context, "Подумай краще", Toast.LENGTH_SHORT).show()
                            if (score > 0) score--
                            binding.tvScore.text = "Твій рахунок: $score"
                        }
                    }
                    counter++
                }

            }
        }
        binding.exercise.firstValue.text = mathProblem.firstValue.toString()
        binding.exercise.secondValue.text = mathProblem.secondValue.toString()
        binding.exercise.symbol.text = "÷"
    }
}