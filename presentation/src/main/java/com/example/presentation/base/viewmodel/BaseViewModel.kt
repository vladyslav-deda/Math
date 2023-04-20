package com.example.presentation.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.moneybalance.BalanceRepository
import com.example.domain.shop.ShopRepository
import com.example.domain.shop.model.ShopItem
import com.example.presentation.base.model.MathematicalEquation

abstract class BaseViewModel(
    private val shopRepository: ShopRepository,
    private val balanceRepository: BalanceRepository
) : ViewModel() {

    private val _currentScore = MutableLiveData(0)
    val currentScore: LiveData<Int> = _currentScore

    protected lateinit var pairOfNumbers: Pair<Int, Int>
    protected var result: Int = 0
    protected lateinit var equationSign: String

    abstract fun generateMathematicalEquation(level: String): MathematicalEquation

    fun increaseScore() {
        val newScore = _currentScore.value?.plus(1)
        _currentScore.postValue(newScore)
    }

    fun decreaseScore() {
        val newScore = _currentScore.value?.minus(1)
        _currentScore.postValue(newScore)
    }

    fun updateBalance(balance: Int) = balanceRepository.updateBalance(balance)

    fun getBalance() = balanceRepository.getBalance()

    fun getSelectedItem(): ShopItem = shopRepository.getSelectedItem()
}