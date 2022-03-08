package com.example.mathwithfinik.ui.mainscreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mathwithfinik.Constants
import com.example.mathwithfinik.MainScreenAdapter
import com.example.mathwithfinik.models.MenuItem
import com.example.mathwithfinik.R
import com.example.mathwithfinik.databinding.MainScreenFragmentBinding
import com.example.mathwithfinik.plus_minus_screen.PlusMinusFragment
import kotlin.concurrent.fixedRateTimer


class MainScreenFragment : Fragment() {

    companion object {
        fun newInstance() = MainScreenFragment()
    }
    private lateinit var sharedPref: SharedPreferences
    private lateinit var viewModel: MainScreenViewModel
    lateinit var binding: MainScreenFragmentBinding
    private val items = ArrayList<MenuItem>()
    private val adapterMenu = MainScreenAdapter(items)


    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPref = context.getSharedPreferences(
            Constants.SHARED_PREFS_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainScreenFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.mspRv.apply {
            if (items.isEmpty()) {
                initArray()
            }
            layoutManager = GridLayoutManager(activity, 2,  LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterMenu
        }

        adapterMenu.onItemClick = { item ->
            when (item.name) {
                context?.getString(R.string.multiply) -> {
                    Navigation.findNavController(binding.root).navigate(R.id.action_mainScreenFragment_to_multiplyFragment)
                }
                context?.getString(R.string.divide) -> {
                    Navigation.findNavController(binding.root).navigate(R.id.action_mainScreenFragment_to_divideFragment)
                }
                context?.getString(R.string.plus_minus) -> {
                    Navigation.findNavController(binding.root).navigate(R.id.action_mainScreenFragment_to_complexityFragment)
                }

                else -> {
                    Toast.makeText(
                        context,
                        "Повтри поки множення, нові функції вже в дорозі",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
        binding.mspTvMoneyBalance.text = sharedPref.getInt(Constants.BALANCE, 0).toString()

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun initArray() {
        items.add(
            MenuItem(
                resources.getDrawable(R.drawable.icon_plus_minus),
                getString(R.string.plus_minus)
            )
        )
        items.add(
            MenuItem(resources.getDrawable(R.drawable.icon_divide), getString(R.string.divide))
        )
        items.add(
            MenuItem(resources.getDrawable(R.drawable.icon_multiply), getString(R.string.multiply))
        )
        items.add(
            MenuItem(resources.getDrawable(R.drawable.icon_zadachi), "Задачі", true)
        )
        items.add(
            MenuItem(resources.getDrawable(R.drawable.icon_shopping_cart), "Магазин",true)
        )
        items.add(
            MenuItem(resources.getDrawable(R.drawable.icon_world), "Налаштування", true)
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainScreenViewModel::class.java]
        // TODO: Use the ViewModel
    }
}