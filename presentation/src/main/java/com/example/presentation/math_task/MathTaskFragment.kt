package com.example.presentation.math_task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.domain.holder.SessionHolder
import com.example.domain.holder.model.Task
import com.example.presentation.R
import com.example.presentation.base.DialogExtensions.showInfoDialog
import com.example.presentation.base.getImageRes
import com.example.presentation.base.getSelectedItem
import com.example.presentation.databinding.MathTaskFragmentBinding
import com.example.presentation.base.RequestState
import com.example.presentation.math_task.viewmodel.MathTaskViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class MathTaskFragment : Fragment() {

    private lateinit var binding: MathTaskFragmentBinding

    private val viewModel by viewModels<MathTaskViewModel>()

    private val anim by lazy {
        AnimationUtils.loadAnimation(binding.root.context, R.anim.scale_alpha_notification)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MathTaskFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeStates()
    }

    private fun generateTask() {
        val indexOfTask = Random.nextInt(0, viewModel.getListOfTasks().lastIndex)
        val mathTask = viewModel.getListOfTasks()[indexOfTask]
        binding.apply {
            answer.text.clear()
            task.text = mathTask.text
            buttonAnswer.setOnClickListener {
                if (answer.text.isEmpty()) return@setOnClickListener
                if (answer.text.toString().toInt() == mathTask.answer) {
                    viewModel.increaseScore()
                    textNotification.apply {
                        visibility = VISIBLE
                        text = "Молодець"
                        startAnimation(anim)
                    }
                    notification.apply {
                        visibility = VISIBLE
                        background =
                            ContextCompat.getDrawable(requireContext(), R.drawable.back_for_item)
                    }
                    imageNotification.setImageResource(
                        SessionHolder.currentUser?.shopItems?.getSelectedItem()?.getImageRes()
                            ?: R.drawable.logo_cat
                    )
                    viewModel.currentScore.value?.let { currentScore ->
                        if (currentScore < 10) {
                            generateTask()
                        } else {
                            endOfRound()
                        }
                    }
                } else {
                    viewModel.decreaseScore()
                    textNotification.apply {
                        visibility = VISIBLE
                        text = "Подумай краще"
                        startAnimation(anim)
                    }
                    notification.apply {
                        visibility = VISIBLE
                        background =
                            ContextCompat.getDrawable(requireContext(), R.drawable.back_red)
                    }
                    imageNotification.setImageResource(
                        SessionHolder.currentUser?.shopItems?.getSelectedItem()?.getImageRes()
                            ?: R.drawable.logo_cat
                    )
                    answer.text.clear()
                }
            }
        }
    }

    private fun endOfRound() {
        requireContext().showInfoDialog(
            text = resources.getString(R.string.result, viewModel.currentScore.value),
            imageRes = SessionHolder.currentUser?.shopItems?.getSelectedItem()?.getImageRes()
                ?: R.drawable.logo_cat,
            okButtonAction = {
                findNavController().popBackStack()
            }
        )
        if (SessionHolder.isUserAuthorized) {
            viewModel.currentScore.value?.let { currentScore ->
                if (currentScore > 0) {
                    val currentBalance = SessionHolder.currentUser?.moneyBalance ?: 0
                    val newBalance = currentBalance + currentScore
                    viewModel.updateMoneyBalance(newBalance)
                }
            }
        }
    }

    private fun observeStates() {
        viewModel.currentScore.observe(viewLifecycleOwner) {
            binding.score.text = resources.getString(R.string.score, it)
        }
        viewModel.requestState.observe(viewLifecycleOwner) {
            when (it) {
                RequestState.Error -> {
                    Snackbar.make(
                        binding.root,
                        "Сталася помилка при завантажені задач, спробуйте знову",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    findNavController().popBackStack()
                }

                is RequestState.Loading -> updateUI(it.isLoading)
                is RequestState.Successful -> {
                    it.data?.children?.forEach { data ->
                        data.getValue(Task::class.java)?.let { data ->
                            viewModel.addTask(data)
                        }
                    }
                    generateTask()
                }
            }
        }
    }

    private fun updateUI(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                mainLayout.visibility = GONE
                progressbar.visibility = VISIBLE
            } else {
                mainLayout.visibility = VISIBLE
                progressbar.visibility = GONE
            }
        }
    }

}