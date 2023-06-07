package com.example.presentation.multiply

import androidx.navigation.fragment.findNavController
import com.example.presentation.base.BaseExerciseFragment
import com.example.presentation.multiply.viewmodel.MultiplyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MultiplyFragment : BaseExerciseFragment<MultiplyViewModel>() {

    override fun getViewModelClass() = MultiplyViewModel::class.java

    override fun actionBackToMainScreen() =
        findNavController().navigate(MultiplyFragmentDirections.actionMultiplyFragmentToHomeFragment())
}