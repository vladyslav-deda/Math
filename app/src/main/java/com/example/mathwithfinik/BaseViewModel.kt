package com.example.mathwithfinik

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mathwithfinik.databinding.ExerciseFragmentBinding
import com.example.mathwithfinik.models.MathProblemModel
import kotlinx.android.synthetic.main.exercise_fragment.view.*
import kotlinx.coroutines.SupervisorJob
import kotlin.random.Random


abstract class BaseViewModel(open val binding: ExerciseFragmentBinding) : ViewModel() {
    var score = 0
    private val viewModelJob = SupervisorJob()
    val scoreLiveData = MutableLiveData<Int>()
    val anim by lazy {
        AnimationUtils.loadAnimation(binding.root.context, R.anim.scale_alpha_notification)
    }

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
                    binding.notification.tv_notification.apply {
                        visibility = View.VISIBLE
                        text = "Молодець"
                        this.startAnimation(anim)
                    }

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
                            binding.notification.tv_notification.apply {
                                visibility = View.VISIBLE
                                text = "Подумай краще"
                                this.startAnimation(anim)
                            }
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
            dlg.setContentView(R.layout.dialog_first_speach_layout)
            dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dlg.findViewById<TextView>(R.id.tv_main_text).text = text
            dlg.findViewById<Button>(R.id.speach_dialog_ok_button).setOnClickListener {
                dlg.dismiss()
                actionBackToMainScreen()
            }
            dlg.show()
        }
    }

    private fun anim(v: View) {
        val anim: Animation = ScaleAnimation(
            1f, 1f,  // Start and end values for the X axis scaling
            0f, 1f,  // Start and end values for the Y axis scaling
            Animation.RELATIVE_TO_SELF, 0f,  // Pivot point of X scaling
            Animation.RELATIVE_TO_SELF, 1f
        ) // Pivot point of Y scaling

        anim.fillAfter = true // Needed to keep the result of the animation

        anim.duration = 1000
        v.startAnimation(anim)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}