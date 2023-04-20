package com.example.data.moneybalance

import android.content.SharedPreferences
import com.example.domain.moneybalance.BalanceRepository
import javax.inject.Inject

class BalanceRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : BalanceRepository {

    override fun getBalance(): Int = sharedPreferences.getInt(BALANCE, 0)

    override fun updateBalance(balance: Int) = sharedPreferences.edit().putInt(BALANCE, balance).apply()

    companion object {
        private const val BALANCE = "balance"
    }
}