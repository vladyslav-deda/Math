package com.example.mathwithfinik

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.Navigation
import com.example.mathwithfinik.databinding.FragmentComplexityBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ComplexityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ComplexityFragment : Fragment() {

    private lateinit var binding: FragmentComplexityBinding
    private var bundle = Bundle()

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
                val result = 'e'
                setFragmentResult("requestKey", bundleOf("bundleKey" to Constants.EASY_CHAR))
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_complexityFragment_to_plusMinusFragment)
            }
            tvMediumLevel.setOnClickListener {
                setFragmentResult("requestKey", bundleOf("bundleKey" to Constants.MEDIUM_CHAR))
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_complexityFragment_to_plusMinusFragment, bundle)
            }
            tvHardLevel.setOnClickListener {
                setFragmentResult("requestKey", bundleOf("bundleKey" to Constants.HARD_CHAR))
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_complexityFragment_to_plusMinusFragment, bundle)
            }
        }
    }
}