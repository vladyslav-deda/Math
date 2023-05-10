package com.example.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.domain.holder.SessionHolder
import com.example.presentation.Constants
import com.example.presentation.R
import com.example.presentation.base.DialogExtensions.showLevelSelectionDialog
import com.example.presentation.base.getSelectedItem
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
            SessionHolder.currentUser?.shopItems?.getSelectedItem()?.icon?.let {
                logoImage.setImageResource(it)
            }
            if (SessionHolder.isUserAuthorized) {
                moneyBalance.apply {
                    visibility = View.VISIBLE
                    text = (SessionHolder.currentUser?.moneyBalance ?: 0).toString()
                }
                moneyImage.visibility = View.VISIBLE
            }
        }
    }

    private fun initRecyclerView() {
        homeAdapter = HomeAdapter { item ->
            when (item.name) {
                getString(R.string.multiply) -> {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMultiplyFragment())
                }

                getString(R.string.divide) -> {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDivideFragment())
                }

                getString(R.string.plus_minus) -> {
                    requireContext().showLevelSelectionDialog(
                        text = "Обери рівень складності прикладів",
                        imageRes = SessionHolder.currentUser?.shopItems?.getSelectedItem()?.icon ?: R.drawable.logo_cat
                    ) { level ->
                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPlusMinusFragment(level))
                    }
                }

                getString(R.string.zadachi) -> {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMathTaskFragment())
                }

                getString(R.string.shop) -> {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToShopFragment())
                }

                getString(R.string.add_task) -> {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddTaskFragment())
                }

                else -> {
                }
            }
        }
        binding.menuItems.adapter = homeAdapter
        homeAdapter?.submitList(getMenuItems())
    }

    private fun getMenuItems(): List<MenuItem> {
        val list = arrayListOf<MenuItem>()
        if (SessionHolder.currentUser?.level == Constants.ADMIN_ACCESS_LEVEL) {
            list.add(
                MenuItem(
                    icon = ContextCompat.getDrawable(requireContext(), R.drawable.add_task),
                    name = getString(R.string.add_task)
                )
            )
        }
        list.addAll(
            listOf(
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
                )
            )
        )
        if (SessionHolder.isUserAuthorized) {
            list.add(
                MenuItem(
                    icon = ContextCompat.getDrawable(requireContext(), R.drawable.icon_shopping_cart),
                    name = getString(R.string.shop)
                )
            )
        }
        return list
    }
}