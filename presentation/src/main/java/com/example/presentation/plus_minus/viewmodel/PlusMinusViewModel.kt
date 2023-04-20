package com.example.presentation.plus_minus.viewmodel

import com.example.domain.moneybalance.BalanceRepository
import com.example.domain.shop.ShopRepository
import com.example.presentation.Constants
import com.example.presentation.base.model.MathematicalEquation
import com.example.presentation.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class PlusMinusViewModel @Inject constructor(
    repository: ShopRepository,
    balanceRepository: BalanceRepository
) : BaseViewModel(repository, balanceRepository) {

    private var isPlusSign = false
    override var equationSign = if (isPlusSign) PLUS_SIGN else MINUS_SIGN

    override fun generateMathematicalEquation(level: String?): MathematicalEquation {
        wrongAnswersList.clear()
        isPlusSign = Random.nextBoolean()
        equationSign = if (isPlusSign) PLUS_SIGN else MINUS_SIGN
        pairOfNumbers = generatePair(level)
        if (isPlusSign) {
            result = pairOfNumbers.first + pairOfNumbers.second
            val maxResultValue = genMaxNumberOfLevel(level)
            while (result > maxResultValue) {
                pairOfNumbers = generatePair(level)
                result = pairOfNumbers.first + pairOfNumbers.second
            }
        } else {
            while (pairOfNumbers.first < pairOfNumbers.second) {
                pairOfNumbers = generatePair(level)
            }
            result = pairOfNumbers.first - pairOfNumbers.second
        }

        var maxValueOfWrongAnswer = if (isPlusSign) {
            result * 2
        } else {
            pairOfNumbers.first + pairOfNumbers.second
        }

        if (maxValueOfWrongAnswer < 5) {
            maxValueOfWrongAnswer = 5
        }

        while (wrongAnswersList.size < SIZE_OF_WRONG_ANSWERS_LIST) {
            val value = Random.nextInt(MIN_VALUE, maxValueOfWrongAnswer)
            if (value != result && !wrongAnswersList.contains(value)) {
                wrongAnswersList.add(value)
            }
        }
        return MathematicalEquation(
            firstValue = pairOfNumbers.first,
            secondValue = pairOfNumbers.second,
            answerValue = result,
            wrongAnswers = wrongAnswersList,
            equationSign = equationSign
        )
    }

    override fun generatePair(level: String?): Pair<Int, Int> {
        val maxValue = genMaxNumberOfLevel(level)
        val firstValue = Random.nextInt(MIN_VALUE, maxValue)
        val secondValue = Random.nextInt(MIN_VALUE, maxValue)
        return Pair(firstValue, secondValue)
    }

    private fun genMaxNumberOfLevel(level: String?) = when (level) {
        Constants.EASY_LEVEL -> 10
        Constants.MEDIUM_LEVEL -> 100
        else -> 500
    }
}