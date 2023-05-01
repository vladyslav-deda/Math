package com.example.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.presentation.R
import com.example.presentation.base.DialogExtensions.showLevelSelectionDialog
import com.example.presentation.databinding.FragmentHomeBinding
import com.example.presentation.home.adapter.HomeAdapter
import com.example.presentation.home.model.MenuItem
import com.example.presentation.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel by viewModels<HomeViewModel>()

    private var homeAdapter: HomeAdapter? = null

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
            logoImage.setImageResource(viewModel.getSelectedItem().icon)
            moneyBalance.text = viewModel.getBalance().toString()
        }
    }

    private fun initRecyclerView() {
        homeAdapter = HomeAdapter { item ->
            when (item.name) {
                resources.getString(R.string.multiply) -> {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMultiplyFragment())
                }
                resources.getString(R.string.divide) -> {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDivideFragment())
                }
                resources.getString(R.string.plus_minus) -> {
                    requireContext().showLevelSelectionDialog(
                        text = "Обери рівень складності прикладів",
                        imageRes = viewModel.getSelectedItem().icon
                    ) { level ->
                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPlusMinusFragment(level))
                    }
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