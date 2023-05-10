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
import com.example.presentation.auth.viewmodel.AuthViewModel
import com.example.presentation.databinding.AuthFragmentBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment() {

    private lateinit var binding: AuthFragmentBinding

    private val viewModel by viewModels<AuthViewModel>()

    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AuthFragmentBinding.inflate(inflater, container, false)
        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.USERS_DB_NAME)
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
    }

    private fun initViews() {
        binding.apply {
            loginButton.setOnClickListener {
                if (viewModel.email.value.isNullOrEmpty() || viewModel.password.value.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), "Не всі поля є заповненими!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                databaseReference.child(viewModel.email.value.toString()).get().addOnCompleteListener {
                    viewModel.setIsLoading(true)
                    if (it.isSuccessful) {
                        if (it.result.exists()) {
                            val user = it.result.getValue(User::class.java) as User
                            if (user.password.equals(viewModel.password.value)) {
                                SessionHolder.currentUser = user
                                SessionHolder.isUserAuthorized = true
                                findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToHomeFragment())
                            } else {
                                Toast.makeText(requireContext(), "Неправильно введений пароль", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(requireContext(), "Користувача з таким нікнеймом не було знайдено", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Сталася помилка під час авторизації, спробуйте ще раз", Toast.LENGTH_SHORT).show()
                    }
                    viewModel.setIsLoading(false)
                }
            }
            emailEdittext.doOnTextChanged { text, _, _, _ ->
                viewModel.updateEmail(text.toString())
            }
            passwordEdittext.doOnTextChanged { text, _, _, _ ->
                viewModel.updatePassword(text.toString())
            }
            registerButton.setOnClickListener {
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