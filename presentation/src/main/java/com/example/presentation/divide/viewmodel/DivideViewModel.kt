package com.example.presentation.divide.viewmodel

import com.example.domain.moneybalance.BalanceRepository
import com.example.domain.shop.ShopRepository
import com.example.presentation.base.model.MathematicalEquation
import com.example.presentation.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class DivideViewModel @Inject constructor(
    repository: ShopRepository,
    balanceRepository: BalanceRepository
) : BaseViewModel(repository, balanceRepository) {

    override var equationSign = DIVIDE_SIGN

    override fun generateMathematicalEquation(level: String?): MathematicalEquation {
        wrongAnswersList.clear()
        pairOfNumbers = generatePair()
        result = pairOfNumbers.first / pairOfNumbers.second
        var maxValueOfWrongAnswer = result * 2
        while (maxValueOfWrongAnswer < 5) {
            maxValueOfWrongAnswer *= 2
        }
        while (wrongAnswersList.size < 3) {
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
        val tmp = Random.nextInt(MIN_VALUE_FOR_DIVIDE, MAX_VALUE_FOR_DIVIDE)
        val secondValue = Random.nextInt(MIN_VALUE_FOR_DIVIDE, MAX_VALUE_FOR_DIVIDE)
        val firstValue = secondValue * tmp
        return Pair(firstValue, secondValue)
    }

    companion object {
        private const val MAX_VALUE_FOR_DIVIDE = 10
        private const val MIN_VALUE_FOR_DIVIDE = 2
    }
}