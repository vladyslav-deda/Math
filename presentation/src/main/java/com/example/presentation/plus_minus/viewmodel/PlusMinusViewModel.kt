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

    override fun generateMathematicalEquation(level: String): MathematicalEquation {
        isPlusSign = Random.nextBoolean()
        equationSign = if (isPlusSign) "+" else "-"
        pairOfNumbers = generatePair(level)
        val wrongAnswersList = ArrayList<Int>()
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

        while (wrongAnswersList.size < 3) {
            val value = Random.nextInt(1, maxValueOfWrongAnswer)
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

    private fun generatePair(level: String): Pair<Int, Int> {
        val maxValue = genMaxNumberOfLevel(level)
        val firstValue: Int = Random.nextInt(1, maxValue)
        val secondValue: Int = Random.nextInt(1, maxValue)
        return Pair(firstValue, secondValue)
    }

    private fun genMaxNumberOfLevel(level: String) = when (level) {
        Constants.EASY_LEVEL -> 10
        Constants.MEDIUM_LEVEL -> 100
        else -> 500
    }
}