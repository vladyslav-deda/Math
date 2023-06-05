package com.example.domain.firebase_users_db.usecase

import com.example.domain.firebase_users_db.FirebaseUsersDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateMoneyBalanceUseCase(private val repository: FirebaseUsersDbRepository) {

    suspend operator fun invoke(
        userName: String,
        newBalance: Int
    ) {
        withContext(Dispatchers.IO) {
            repository.updateMoneyBalance(userName, newBalance)
        }
    }
}