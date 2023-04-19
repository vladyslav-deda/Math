package com.example.presentation.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.presentation.Constants
import com.example.presentation.R
import com.example.presentation.databinding.FragmentHomeBinding
import com.example.presentation.home.adapter.HomeAdapter
import com.example.presentation.home.model.MenuItem
import com.example.presentation.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var sharedPref: SharedPreferences

    private var homeAdapter: HomeAdapter? = null

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
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initViews()
    }

    private fun initViews() {
        binding.apply {
//            Glide
//                .with(requireContext())
//                .load(viewModel.getSelectedItem().icon)
//                .into(logoImage)
            moneyBalance.text = sharedPref.getInt(Constants.BALANCE, 0).toString()
        }
    }

    private fun initRecyclerView() {
        homeAdapter = HomeAdapter { item ->
            when (item.name) {
                resources.getString(R.string.multiply) -> {
//                    findNavController().navigate(R.id.action_mainScreenFragment_to_multiplyFragment)
                }
                resources.getString(R.string.divide) -> {
//                    findNavController().navigate(R.id.action_mainScreenFragment_to_divideFragment)
                }
                resources.getString(R.string.plus_minus) -> {
//                    findNavController().navigate(R.id.action_mainScreenFragment_to_complexityFragment)
                }
                resources.getString(R.string.zadachi) -> {
//                    showDialog(context)
                }
                resources.getString(R.string.shop) -> {
//                    findNavController().navigate(R.id.action_mainScreenFragment_to_shopFragment)
                }
                else -> {
                }
            }
        }
        binding.menuItems.adapter = homeAdapter
        homeAdapter?.submitList(getMenuItems())
    }

    private fun getMenuItems(): List<MenuItem> {
        return listOf(
            MenuItem(
                icon = ContextCompat.getDrawable(requireContext(), R.drawable.icon_plus_minus),
                name = getString(R.string.plus_minus)
            ),
            MenuItem(
                icon = ContextCompat.getDrawable(requireContext(), R.drawable.icon_divide),
                name = getString(R.string.divide)
            ),
            MenuItem(
                icon = ContextCompat.getDrawable(requireContext(), R.drawable.icon_multiply),
                name = getString(R.string.multiply)
            ),
            MenuItem(
                icon = ContextCompat.getDrawable(requireContext(), R.drawable.icon_zadachi),
                name = getString(R.string.zadachi)
            ),
            MenuItem(
                icon = ContextCompat.getDrawable(requireContext(), R.drawable.icon_shopping_cart),
                name = getString(R.string.shop)
            )
        )
    }
}