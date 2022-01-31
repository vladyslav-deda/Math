package com.example.mathwithfinik.multiply

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mathwithfinik.R

class MultiplyFragment : Fragment() {

    companion object {
        fun newInstance() = MultiplyFragment()
    }

    private lateinit var viewModel: MultiplyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.multiply_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MultiplyViewModel::class.java)
        // TODO: Use the ViewModel
    }

}