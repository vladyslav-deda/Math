package com.example.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.domain.holder.SessionHolder
import com.example.domain.holder.model.User
import com.example.domain.shop.model.ShopItem
import com.example.presentation.Constants.ADMIN_ACCESS_LEVEL
import com.example.presentation.Constants.PUPIL_ACCESS_LEVEL
import com.example.presentation.Constants.USERS_DB_NAME
import com.example.presentation.R
import com.example.presentation.databinding.RegisterFragmentBinding
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
        databaseReference = FirebaseDatabase.getInstance().getReference(USERS_DB_NAME)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeStates()
        binding.apply {
            registerButton.setOnClickListener {
                if (viewModel.email.value.isNullOrEmpty() || viewModel.password.value.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), "Не всі поля було заповнено", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                databaseReference.child(viewModel.email.value.toString()).get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        if (it.result.exists()) {
                            Toast.makeText(
                                requireContext(),
                                "Спробуйте використати інший нікнейм, цей нікнейм вже зайнятий",
                                Toast.LENGTH_SHORT
                            ).show()
                            clearFields()
                        } else {
                            val level = if (viewModel.isAdmin.value == true) {
                                ADMIN_ACCESS_LEVEL
                            } else {
                                PUPIL_ACCESS_LEVEL
                            }
                            registerNewUser(level)
                        }
                    } else {
                        Toast.makeText(requireContext(), "Сталася помилка під час реєстрації, спробуйте ще раз", Toast.LENGTH_SHORT).show()
                        clearFields()
                    }
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
                    root.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.amber_yellow))
                    registrationLabel.text = "Реєстрація адміністратора"
                }
            }
        }
    }

    private fun clearFields() {
        binding.apply {
            emailEdittext.text?.clear()
            passwordEdittext.text?.clear()
        }
    }

    private fun registerNewUser(level: String) {
        val newUser = User(
            userName = viewModel.email.value,
            password = viewModel.password.value,
            moneyBalance = 0,
            level = level,
            shopItems = initialListOfShopItems()
        )
        databaseReference.child(viewModel.email.value.toString()).setValue(newUser).addOnCompleteListener {
            if (it.isSuccessful) {
                SessionHolder.currentUser = newUser
                SessionHolder.isUserAuthorized = true
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
            } else {
                Toast.makeText(requireContext(), "Під час реєстрації виникла помилка, спробуйте знову", Toast.LENGTH_SHORT).show()
                clearFields()
            }
        }
    }

    private fun initialListOfShopItems() =
        listOf(
            ShopItem(
                1,
                R.drawable.logo_cat,
                0,
                isBought = true,
                isSelected = true

            ),
            ShopItem(
                2,
                R.drawable.crab,
                20
            ),
            ShopItem(
                3,
                R.drawable.elephant,
                40
            ),
            ShopItem(
                4,
                R.drawable.giraffe,
                60
            ),
            ShopItem(
                5,
                R.drawable.gybka_bob,
                80
            ),
            ShopItem(
                6,
                R.drawable.hero,
                100
            ),
            ShopItem(
                7,
                R.drawable.lion2,
                120
            ),
            ShopItem(
                8,
                R.drawable.lion3,
                140
            ),
            ShopItem(
                9,
                R.drawable.monkey,
                160
            ),
            ShopItem(
                10,
                R.drawable.patrick,
                180
            ),
            ShopItem(
                11,
                R.drawable.pig,
                200
            ),
            ShopItem(
                12,
                R.drawable.rhinocerous,
                220
            ),
            ShopItem(
                13,
                R.drawable.snail,
                240
            ),
            ShopItem(
                14,
                R.drawable.svunka_pepa,
                260
            )
        )

    companion object {
        private const val MAX_ROTATION_DEGREES = 360f
    }
}