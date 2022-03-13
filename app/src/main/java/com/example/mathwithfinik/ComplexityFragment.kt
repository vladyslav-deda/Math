package com.example.mathwithfinik

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mathwithfinik.databinding.FragmentComplexityBinding

class ComplexityFragment : Fragment() {

    private lateinit var binding: FragmentComplexityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentComplexityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            tvEasyLevel.setOnClickListener {
                navigate(Constants.EASY_CHAR)
            }
            tvMediumLevel.setOnClickListener {
                navigate(Constants.MEDIUM_CHAR)
            }
            tvHardLevel.setOnClickListener {
                navigate(Constants.HARD_CHAR)
            }
        }
    }

    private fun navigate(level: String) = findNavController().navigate(
        ComplexityFragmentDirections.actionComplexityFragmentToPlusMinusFragment(
            level
        )
    )
}