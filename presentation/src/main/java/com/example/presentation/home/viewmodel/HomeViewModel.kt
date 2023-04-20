package com.example.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.moneybalance.BalanceRepository
import com.example.domain.shop.ShopRepository
import com.example.domain.shop.model.ShopItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val shopRepository: ShopRepository,
    balanceRepository: BalanceRepository
) : ViewModel() {

    fun getSelectedItem(): ShopItem = shopRepository.getSelectedItem()
}