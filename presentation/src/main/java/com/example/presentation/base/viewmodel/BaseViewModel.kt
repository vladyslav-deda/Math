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
    protected abstract var equationSign: String
    protected var wrongAnswersList = ArrayList<Int>()

    abstract fun generateMathematicalEquation(level: String? = null): MathematicalEquation

    abstract fun generatePair(level: String? = null): Pair<Int, Int>

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

    companion object {
        const val MIN_VALUE = 1
        const val SIZE_OF_WRONG_ANSWERS_LIST = 3
        const val PLUS_SIGN = "+"
        const val MINUS_SIGN = "-"
        const val MULTIPLY_SIGN = "×"
        const val DIVIDE_SIGN = ":"
    }
}