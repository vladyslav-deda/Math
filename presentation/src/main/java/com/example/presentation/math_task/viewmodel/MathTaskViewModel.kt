package com.example.presentation.math_task.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.moneybalance.BalanceRepository
import com.example.domain.shop.ShopRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MathTaskViewModel @Inject constructor(
    private val shopRepository: ShopRepository,
    private val balanceRepository: BalanceRepository
) : ViewModel() {

}