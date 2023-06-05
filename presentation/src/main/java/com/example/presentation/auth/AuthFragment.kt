package com.example.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.domain.holder.SessionHolder
import com.example.domain.holder.model.User
import com.example.presentation.Constants
import com.example.presentation.R
import com.example.presentation.auth.viewmodel.AuthRequestState
import com.example.presentation.auth.viewmodel.AuthViewModel
import com.example.presentation.databinding.AuthFragmentBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment() {

    private lateinit var binding: AuthFragmentBinding

    private val viewModel by viewModels<AuthViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AuthFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeState()
    }

    private fun observeState() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            updateUI(it)
        }
        viewModel.requestState.observe(viewLifecycleOwner) {
            when (it) {
                AuthRequestState.AuthError -> {
                    Snackbar.make(
                        binding.root,
                        "Сталася помилка під час авторизації, спробуйте ще раз",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                AuthRequestState.InvalidPassword -> {
                    Snackbar.make(
                        binding.root,
                        "Неправильно введений пароль",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                is AuthRequestState.Loading -> viewModel.setIsLoading(it.isLoading)
                is AuthRequestState.Successful -> {
                    SessionHolder.currentUser = it.user
                    SessionHolder.isUserAuthorized = true
                    findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToHomeFragment())
                }

                AuthRequestState.UserWasNotFound -> {
                    Snackbar.make(
                        binding.root,
                        "Користувача з таким нікнеймом не було знайдено",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun initViews() {
        binding.apply {
            loginButton.setOnClickListener {
                if (viewModel.nickname.value.isNullOrEmpty() || viewModel.password.value.isNullOrEmpty()) {
                    Snackbar.make(
                        binding.root,
                        R.string.empty_input_fields,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                viewModel.authUser()
            }
            emailEdittext.doOnTextChanged { text, _, _, _ ->
                viewModel.updateEmail(text.toString())
            }
            passwordEdittext.doOnTextChanged { text, _, _, _ ->
                viewModel.updatePassword(text.toString())
            }
            register.setOnClickListener {
                findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToRegisterFragment())
            }
            continueWithoutAuth.setOnClickListener {
                SessionHolder.isUserAuthorized = false
                findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToHomeFragment())
            }
        }
    }

    private fun updateUI(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                mainLayout.visibility = View.GONE
                progressbar.visibility = View.VISIBLE
            } else {
                mainLayout.visibility = View.VISIBLE
                progressbar.visibility = View.GONE
            }
        }
    }
}