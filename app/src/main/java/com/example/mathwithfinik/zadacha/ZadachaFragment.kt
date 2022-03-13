package com.example.mathwithfinik.zadacha

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mathwithfinik.R

class ZadachaFragment : Fragment() {

    companion object {
        fun newInstance() = ZadachaFragment()
    }

    private lateinit var viewModel: ZadachaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.zadacha_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ZadachaViewModel::class.java)
        // TODO: Use the ViewModel
    }

}