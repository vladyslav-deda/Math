package com.example.mathwithfinik

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.mathwithfinik.databinding.ExerciseFragmentBinding
import com.example.mathwithfinik.models.MathExerciseModel
import com.example.mathwithfinik.room_db.ShopRepository
import kotlinx.android.synthetic.main.exercise_fragment.view.*
import kotlin.random.Random


abstract class BaseViewModel(open val binding: ExerciseFragmentBinding) : ViewModel() {
    var score = 0
    val scoreLiveData = MutableLiveData<Int>()
    val anim by lazy {
        AnimationUtils.loadAnimation(binding.root.context, R.anim.scale_alpha_notification)
    }

    fun generateNewExercise(level: String? = null) {
        val mathExercise = makeMathExerciseModel(level)
        val indexOfTrueAnswer: Int = Random.nextInt(0, 3)
        val arrayOfButtons = ArrayList<Button>()
        updateScore(score)
        arrayOfButtons.apply {
            add(binding.setOfFourButtons.button1)
            add(binding.setOfFourButtons.button2)
            add(binding.setOfFourButtons.button3)
            add(binding.setOfFourButtons.button4)
            get(indexOfTrueAnswer).apply {
                text = mathExercise.answerValue.toString()
                setOnClickListener {
                    score++
                    binding.notification.visibility = View.VISIBLE
                    binding.notification.tv_notification.apply {
                        visibility = View.VISIBLE
                        text = "Молодець"
                        this.startAnimation(anim)
                    }
                    binding.notification.background =
                        context?.getDrawable(R.drawable.back_for_item)
                    Glide
                        .with(this)
                        .load(ShopRepository(context).getSelected().icon)
                        .into(binding.imageNotification)

                    updateScore(score)
                    this@BaseViewModel.generateNewExercise(level)
                }
            }
            var counter = 0
            for (i in 0..3) {
                if (indexOfTrueAnswer != i) {
                    arrayOfButtons[i].apply {
                        text = mathExercise.wrongAnswers[counter].toString()
                        setOnClickListener {
                            binding.apply {
                                notification.visibility = View.VISIBLE
                                notification.tv_notification.apply {
                                    visibility = View.VISIBLE
                                    text = "Подумай краще"
                                    this.startAnimation(anim)
                                }
                                notification.background =
                                    context?.getDrawable(R.drawable.back_red)
                            }
                            Glide
                                .with(this)
                                .load(ShopRepository(context).getSelected().icon)
                                .into(binding.imageNotification)
                            if (score > 0) score--
                            updateScore(score)
                        }
                    }
                    counter++
                }

            }
        }

        binding.exercise.apply {
            firstValue.text = mathExercise.firstValue.toString()
            secondValue.text = mathExercise.secondValue.toString()
        }
    }

    abstract fun makeMathExerciseModel(level: String? = null): MathExerciseModel

    abstract fun actionBackToMainScreen()

    private fun updateScore(score: Int) = scoreLiveData.postValue(score)

    fun showDialog(context: Context?, text: String) {
        val dialog = context?.let { Dialog(it) }
        dialog?.let { dlg ->
            dlg.apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                setCancelable(false)
                setContentView(R.layout.dialog_first_speach_layout)
                findViewById<AppCompatImageView>(R.id.first_speach_image_finik).setImageResource(
                    ShopRepository(context).getSelected().icon
                )
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                findViewById<TextView>(R.id.tv_main_text).text = text
                findViewById<Button>(R.id.speach_dialog_ok_button).setOnClickListener {
                    dismiss()
                    actionBackToMainScreen()
                }
                show()
            }
        }
    }
}