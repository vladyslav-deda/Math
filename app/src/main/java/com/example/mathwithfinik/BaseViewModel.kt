package com.example.mathwithfinik

import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.mathwithfinik.databinding.ExerciseFragmentBinding
import com.example.mathwithfinik.multiply.MathProblemModel
import kotlinx.coroutines.SupervisorJob
import kotlin.random.Random

abstract class BaseViewModel : ViewModel() {
    var score = 0
    private val viewModelJob = SupervisorJob()

    open fun generateNewExercise(binding: ExerciseFragmentBinding) {
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
    }

    abstract fun makeMathProblemModel(): MathProblemModel

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}