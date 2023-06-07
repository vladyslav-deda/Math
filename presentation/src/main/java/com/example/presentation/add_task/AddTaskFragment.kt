package com.example.presentation.add_task

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import com.example.presentation.R
import com.example.presentation.add_task.viewmodel.AddTaskViewModel
import com.example.presentation.base.RequestState
import com.example.presentation.base.extension.showSnackBar
import com.example.presentation.databinding.AddTaskFragmentBinding
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
                    binding.root.showSnackBar(getString(R.string.empty_input_fields))
                    return@setOnClickListener
                }
                hideKeyboard()
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
                    binding.root.showSnackBar(getString(R.string.task_was_added_successfully))
                }

                else -> {
                    binding.root.showSnackBar(getString(R.string.task_was_not_added))
                }
            }
            clearFields()
            binding.root.rootView.clearFocus()
        }
    }

    private fun clearFields() {
        binding.apply {
            taskEdittext.text?.clear()
            answerEdittext.text?.clear()
        }
    }

    private fun hideKeyboard() =
        binding.root.findFocus()?.let {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
}