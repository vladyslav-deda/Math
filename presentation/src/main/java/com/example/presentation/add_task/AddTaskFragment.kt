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
import com.example.presentation.databinding.AddTaskFragmentBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskFragment : Fragment() {

    private lateinit var binding: AddTaskFragmentBinding

    private val viewModel by viewModels<AddTaskViewModel>()

    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddTaskFragmentBinding.inflate(inflater, container, false)
        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.TASKS_DB_NAME)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            addTask.setOnClickListener {
                if (taskEdittext.text.isNullOrEmpty() || answerEdittext.text.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), "Не всі поля було заповнено", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                databaseReference.push()
                    .setValue(Task(text = taskEdittext.text.toString(), answer = answerEdittext.text.toString().toInt()))
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                requireContext(),
                                "Задачу додано успішно",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Під час додавання задачі виникла помилка, спробуйте знову",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }

}