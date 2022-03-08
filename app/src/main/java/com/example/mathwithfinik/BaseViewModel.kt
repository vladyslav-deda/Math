package com.example.mathwithfinik

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.mathwithfinik.databinding.ExerciseFragmentBinding
import com.example.mathwithfinik.models.MathProblemModel
import kotlinx.coroutines.SupervisorJob
import kotlin.random.Random

abstract class BaseViewModel(open val binding: ExerciseFragmentBinding) : ViewModel() {
    var score = 0
    private val viewModelJob = SupervisorJob()


    open fun generateNewExercise(level: Char? = null) {
        val mathProblem = makeMathProblemModel(level)
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
                    generateNewExercise()
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

    abstract fun makeMathProblemModel(level: Char? = null): MathProblemModel

    abstract fun actionBackToMainScreean()


    fun showDialog(context: Context?, text: String) {
        val dialog = context?.let { Dialog(it) }
        dialog?.let { dlg ->
            dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dlg.setCancelable(false)
            dlg.setContentView(R.layout.dialog_layout)
            dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dlg.findViewById<TextView>(R.id.tv_main_text).text = text
            dlg.findViewById<Button>(R.id.speach_dialog_ok_button).setOnClickListener {
                dlg.dismiss()
                actionBackToMainScreean()
//                binding.root.findNavController()
//                    .navigate(R.id.action_divideFragment_to_mainScreenFragment)
            }
            dlg.show()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}