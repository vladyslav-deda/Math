package com.example.mathwithfinik.plus_minus_screen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mathwithfinik.R

class PlusMinusFragment : Fragment() {

    companion object {
        fun newInstance() = PlusMinusFragment()
    }

    private lateinit var viewModel: PlusMinusViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.plus_minus_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlusMinusViewModel::class.java)
        // TODO: Use the ViewModel
    }

}