package com.example.presentation.base

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.presentation.R
import com.example.presentation.base.DialogExtensions.showInfoDialog
import com.example.presentation.base.viewmodel.BaseViewModel
import com.example.presentation.databinding.BaseFragmentForExerciseBinding
import kotlin.random.Random

abstract class BaseExerciseFragment<VM : BaseViewModel> : Fragment() {

    protected lateinit var binding: BaseFragmentForExerciseBinding

    protected lateinit var viewModel: VM

    private val anim by lazy {
        AnimationUtils.loadAnimation(binding.root.context, R.anim.scale_alpha_notification)
    }

    private lateinit var timer: CountDownTimer

    abstract fun getViewModelClass(): Class<VM>

    abstract fun actionBackToMainScreen()

    open fun getLevel(): String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BaseFragmentForExerciseBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[getViewModelClass()]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timer = object : CountDownTimer(TIMER_DURATION, TIMER_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                val second = millisUntilFinished / TIMER_INTERVAL
                binding.apply {
                    progressbar.progress = second.toInt()
                    tvSecondsLeft.text = resources.getString(R.string.left_time, second)
                }
            }

            override fun onFinish() {
                viewModel.currentScore.value?.let { currentScore ->
                    if (currentScore > 0) {
                        val currentBalance = viewModel.getBalance()
                        val newBalance = currentBalance + currentScore
                        viewModel.updateBalance(newBalance)
                    }

                }
                requireContext().showInfoDialog(
                    text = resources.getString(R.string.result, viewModel.currentScore.value),
                    imageRes = viewModel.getSelectedItem().icon,
                    okButtonAction = { actionBackToMainScreen() }
                )
            }
        }
        timer.start()

        observeStates()
        generateNewEquation(getLevel())
    }

    private fun observeStates() {
        viewModel.currentScore.observe(viewLifecycleOwner) {
            binding.tvScore.text = resources.getString(R.string.score, it)
        }
    }

    private fun generateNewEquation(level: String) {
        val mathEquation = viewModel.generateMathematicalEquation(level)
        val indexOfTrueAnswer: Int = Random.nextInt(0, 3)
        val arrayOfButtons = ArrayList<Button>()
        binding.exercise.sign.text = mathEquation.equationSign
        binding.apply {
            arrayOfButtons.apply {
                with(setOfFourButtons) {
                    add(button1)
                    add(button2)
                    add(button3)
                    add(button4)
                }
                get(indexOfTrueAnswer).apply {
                    text = mathEquation.answerValue.toString()
                    setOnClickListener {
                        viewModel.increaseScore()
                        textNotification.apply {
                            visibility = View.VISIBLE
                            text = "Молодець"
                            startAnimation(anim)
                        }
                        notification.apply {
                            visibility = View.VISIBLE
                            background = ContextCompat.getDrawable(requireContext(), R.drawable.back_for_item)
                        }
                        imageNotification.setImageResource(viewModel.getSelectedItem().icon)
                        generateNewEquation(level)
                    }
                }
                var counter = 0
                for (i in 0..3) {
                    if (indexOfTrueAnswer != i) {
                        arrayOfButtons[i].apply {
                            text = mathEquation.wrongAnswers[counter].toString()
                            setOnClickListener {
                                textNotification.apply {
                                    visibility = View.VISIBLE
                                    text = "Подумай краще"
                                    startAnimation(anim)
                                }
                                notification.apply {
                                    visibility = View.VISIBLE
                                    background = ContextCompat.getDrawable(requireContext(), R.drawable.back_red)
                                }
                                imageNotification.setImageResource(viewModel.getSelectedItem().icon)
                                viewModel.currentScore.value?.let { currentScore ->
                                    if (currentScore > 0) {
                                        viewModel.decreaseScore()
                                    }
                                }
                            }
                        }
                        counter++
                    }
                }
            }
        }

        binding.exercise.apply {
            firstValue.text = mathEquation.firstValue.toString()
            secondValue.text = mathEquation.secondValue.toString()
        }
    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
    }

    companion object {
        private const val TIMER_INTERVAL: Long = 1000
        private const val TIMER_DURATION: Long = 40000
    }
}