package com.example.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.domain.holder.SessionHolder
import com.example.presentation.R
import com.example.presentation.auth.viewmodel.AuthRequestState
import com.example.presentation.auth.viewmodel.AuthViewModel
import com.example.presentation.base.extension.showSnackBar
import com.example.presentation.databinding.AuthFragmentBinding
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
                    binding.root.showSnackBar(getString(R.string.auth_error))
                }

                AuthRequestState.InvalidPassword -> {
                    binding.root.showSnackBar(getString(R.string.invalid_password))
                }

                is AuthRequestState.Loading -> viewModel.setIsLoading(it.isLoading)
                is AuthRequestState.Successful -> {
                    SessionHolder.currentUser = it.user
                    SessionHolder.isUserAuthorized = true
                    findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToHomeFragment())
                }

                AuthRequestState.UserWasNotFound -> {
                    binding.root.showSnackBar(getString(R.string.invalid_nickname))
                }
            }
        }
    }

    private fun initViews() {
        binding.apply {
            loginButton.setOnClickListener {
                if (viewModel.nickname.value.isNullOrEmpty() || viewModel.password.value.isNullOrEmpty()) {
                    binding.root.showSnackBar(getString(R.string.empty_input_fields))
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