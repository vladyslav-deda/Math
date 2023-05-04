package com.example.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.presentation.databinding.RegisterFragmentBinding
import com.example.presentation.register.model.User
import com.example.presentation.register.viewmodel.RegisterViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: RegisterFragmentBinding

    private val viewModel by viewModels<RegisterViewModel>()

    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RegisterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        binding.registerButton.setOnClickListener {
            if (viewModel.email.value.isNullOrEmpty() || viewModel.password.value.isNullOrEmpty()) {
                return@setOnClickListener
            }
            val newUser = User(viewModel.email.value, viewModel.password.value, 20)
            databaseReference.child(viewModel.email.value.toString()).setValue(newUser).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(requireContext(), "Account created", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Account was not created", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.apply {
            emailEdittext.doOnTextChanged { text, _, _, _ ->
                viewModel.updateEmail(text.toString())
            }
            passwordEdittext.doOnTextChanged { text, _, _, _ ->
                viewModel.updatePassword(text.toString())
            }
        }

    }
}