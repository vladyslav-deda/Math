package com.example.domain.moneybalance

interface BalanceRepository {

    fun getBalance(): Int

    fun updateBalance(balance: Int)
}