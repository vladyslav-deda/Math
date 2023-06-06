package com.example.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.presentation.R
import com.example.presentation.databinding.RegisterFragmentBinding
import com.example.presentation.register.viewmodel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: RegisterFragmentBinding

    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RegisterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeStates()
        binding.apply {
            registerButton.setOnClickListener {
                if (viewModel.nickname.value.isNullOrEmpty() || viewModel.password.value.isNullOrEmpty()) {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.empty_input_fields),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                val isUserRegistered = viewModel.checkingIsUserRegistered()
                if (isUserRegistered){
                    Snackbar.make(
                        binding.root,
                        getString(R.string.try_to_use_another_nickname),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    clearInputFields()
                } else {
                    viewModel.registerNewUser()
                }
            }
            emailEdittext.doOnTextChanged { text, _, _, _ ->
                viewModel.updateEmail(text.toString())
            }
            passwordEdittext.doOnTextChanged { text, _, _, _ ->
                viewModel.updatePassword(text.toString())
            }
            logoImage.setOnClickListener {
                if ((viewModel.rotationDegrees.value ?: 0f) < MAX_ROTATION_DEGREES) {
                    viewModel.increaseDegreesOfRotation()
                }
            }
        }
    }

    private fun observeStates() {
        viewModel.rotationDegrees.observe(viewLifecycleOwner) {
            binding.logoImage.apply {
                rotation = it
            }
            if (it == MAX_ROTATION_DEGREES) {
                viewModel.switchToAdminRegistrationMode()
            }
        }
        viewModel.isAdmin.observe(viewLifecycleOwner) { isAdmin ->
            if (isAdmin) {
                binding.apply {
                    root.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.amber_yellow
                        )
                    )
                    registrationLabel.text = "Реєстрація адміністратора"
                }
            }
        }
        viewModel.isRegistrationSuccessful.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
            } else {
                Snackbar.make(
                    binding.root,
                    getString(R.string.registration_error),
                    Snackbar.LENGTH_SHORT
                ).show()
                clearInputFields()
            }
        }
    }

    private fun clearInputFields() {
        binding.apply {
            emailEdittext.text?.clear()
            passwordEdittext.text?.clear()
        }
    }

    companion object {
        private const val MAX_ROTATION_DEGREES = 360f
    }
}