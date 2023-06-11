package com.example.presentation.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.holder.SessionHolder
import com.example.presentation.R
import com.example.presentation.base.extension.showSnackBar
import com.example.presentation.databinding.ShopFragmentBinding
import com.example.presentation.shop.adapter.ShopAdapter
import com.example.presentation.shop.viewmodel.ShopViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopFragment : Fragment() {

    private lateinit var binding: ShopFragmentBinding

    private val viewModel by viewModels<ShopViewModel>()

    private var shopAdapter: ShopAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ShopFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        updateMoneyBalanceOnUI()
        observeStates()
    }

    private fun observeStates() {
        viewModel.shopItems.observe(viewLifecycleOwner) {
            shopAdapter?.submitList(it)
        }
    }

    private fun initRecyclerView() {
        shopAdapter = ShopAdapter {
            when {
                it.isBought && !it.isSelected -> {
                    viewModel.updateStatusOfShopItem(it, setItemStatusAsSelected = true)
                }

                it.isSelected -> {
                    binding.root.showSnackBar(getString(R.string.hero_has_been_selected))
                }

                else -> {
                    if ((SessionHolder.currentUser?.moneyBalance ?: 0) >= (it.price ?: 0)) {
                        val newBalance = SessionHolder.currentUser?.moneyBalance?.minus(it.price ?: 0) ?: 0
                        viewModel.updateMoneyBalance(newBalance)
                        updateMoneyBalanceOnUI()
                        viewModel.updateStatusOfShopItem(it, setItemStatusAsBought = true)
                    } else {
                        binding.root.showSnackBar(getString(R.string.you_dont_have_enough_coins))
                    }
                }
            }
        }
        binding.shopItems.apply {
            layoutManager = GridLayoutManager(activity, 2, LinearLayoutManager.HORIZONTAL, false)
            adapter = shopAdapter
        }
        SessionHolder.currentUser?.shopItems?.let {
            shopAdapter?.submitList(it)
        }
    }

    private fun updateMoneyBalanceOnUI() {
        binding.moneyBalance.text = (SessionHolder.currentUser?.moneyBalance ?: 0).toString()
    }
}