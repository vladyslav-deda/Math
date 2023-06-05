package com.example.presentation.plus_minus

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.presentation.Constants
import com.example.presentation.base.BaseExerciseFragment
import com.example.presentation.plus_minus.viewmodel.PlusMinusViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlusMinusFragment : BaseExerciseFragment<PlusMinusViewModel>() {

    override fun getViewModelClass() = PlusMinusViewModel::class.java

    override fun actionBackToMainScreen() =
            findNavController().navigate(PlusMinusFragmentDirections.actionPlusMinusFragmentToHomeFragment())

    private val args: PlusMinusFragmentArgs by navArgs()

    override fun getLevel() = args.level ?: ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (args.level.equals(Constants.HARD_LEVEL)) {
            binding.exercise.apply {
                firstValue.textSize = 60F
                secondValue.textSize = 60F
                sign.textSize = 60F
            }
        }
    }
}
