package com.example.mathwithfinik

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathwithfinik.databinding.ExerciseFragmentBinding
import com.example.mathwithfinik.models.MathProblemModel
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

abstract class BaseViewModel(open val binding: ExerciseFragmentBinding) : ViewModel() {
    var score = 0
    private val viewModelJob = SupervisorJob()
    val scoreLiveData = MutableLiveData<Int>()

    open fun generateNewExercise(level: String? = null) {
        val mathProblem = makeMathProblemModel(level)
        val indexOfTrueAnswer: Int = Random.nextInt(0, 3)
        val arrayOfButtons = ArrayList<Button>()
        updateScore(score)
        arrayOfButtons.apply {
            add(binding.setOfFourButtons.button1)
            add(binding.setOfFourButtons.button2)
            add(binding.setOfFourButtons.button3)
            add(binding.setOfFourButtons.button4)
            get(indexOfTrueAnswer).apply {
                text = mathProblem.answerValue.toString()
                setOnClickListener {
                    score++
                    updateScore(score)
                     this@BaseViewModel.generateNewExercise(level)
                }
            }
            var counter = 0
            for (i in 0..3) {
                if (indexOfTrueAnswer != i) {
                    arrayOfButtons[i].apply {
                        text = mathProblem.wrongAnswers[counter].toString()
                        setOnClickListener {
                            if (score > 0) score--
                            updateScore(score)
                        }
                    }
                    counter++
                }

            }
        }

        binding.exercise.firstValue.text = mathProblem.firstValue.toString()
        binding.exercise.secondValue.text = mathProblem.secondValue.toString()
    }

    abstract fun makeMathProblemModel(level: String? = null): MathProblemModel

    abstract fun actionBackToMainScreen()

    private fun updateScore(score: Int) = scoreLiveData.postValue(score)

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
                actionBackToMainScreen()
            }
            dlg.show()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}