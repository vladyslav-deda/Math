package com.example.presentation.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.firebase_users_db.usecase.UpdateMoneyBalanceUseCase
import com.example.domain.holder.SessionHolder
import com.example.presentation.base.model.MathematicalEquation
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    private val updateMoneyBalanceUseCase: UpdateMoneyBalanceUseCase
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
        _currentScore.value?.let { currentScore ->
            if (currentScore > 0) {
                val newScore = currentScore - 1
                _currentScore.postValue(newScore)
            }
        }
    }

    fun updateMoneyBalance(
        newBalance: Int,
    ) {
        SessionHolder.currentUser?.userName?.let {
            viewModelScope.launch {
                updateMoneyBalanceUseCase.invoke(it, newBalance)
            }
        }
        SessionHolder.currentUser?.moneyBalance = newBalance
    }

    companion object {
        const val MIN_VALUE = 1
        const val SIZE_OF_WRONG_ANSWERS_LIST = 3
        const val PLUS_SIGN = "+"
        const val MINUS_SIGN = "-"
        const val MULTIPLY_SIGN = "×"
        const val DIVIDE_SIGN = ":"
    }
}