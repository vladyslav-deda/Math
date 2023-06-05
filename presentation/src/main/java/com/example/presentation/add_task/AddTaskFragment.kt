package com.example.presentation.add_task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.domain.holder.model.Task
import com.example.presentation.Constants
import com.example.presentation.R
import com.example.presentation.base.RequestState
import com.example.presentation.databinding.AddTaskFragmentBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskFragment : Fragment() {

    private lateinit var binding: AddTaskFragmentBinding

    private val viewModel by viewModels<AddTaskViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddTaskFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeStates()
        binding.apply {
            addTask.setOnClickListener {
                if (taskEdittext.text.isNullOrEmpty() || answerEdittext.text.isNullOrEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.empty_input_fields),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                viewModel.addTask(
                    textOfTask = taskEdittext.text.toString(),
                    answerOfTask = answerEdittext.text.toString().toInt()
                )
            }
        }
    }

    private fun observeStates() {
        viewModel.requestState.observe(viewLifecycleOwner) {
            when (it) {
                is RequestState.Successful -> {
                    Snackbar.make(
                        binding.root,
                        "Задачу додано успішно",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    Snackbar.make(
                        binding.root,
                        "Під час додавання задачі виникла помилка, спробуйте знову",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}