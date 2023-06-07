package com.example.presentation.divide

import androidx.navigation.fragment.findNavController
import com.example.presentation.base.BaseExerciseFragment
import com.example.presentation.divide.viewmodel.DivideViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DivideFragment : BaseExerciseFragment<DivideViewModel>() {

    override fun getViewModelClass() = DivideViewModel::class.java

    override fun actionBackToMainScreen() =
        findNavController().navigate(DivideFragmentDirections.actionDivideFragmentToHomeFragment())
}