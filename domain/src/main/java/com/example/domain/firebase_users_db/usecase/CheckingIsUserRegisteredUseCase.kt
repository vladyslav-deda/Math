package com.example.domain.firebase_users_db.usecase

import com.example.domain.firebase_users_db.FirebaseUsersDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CheckingIsUserRegisteredUseCase(private val repository: FirebaseUsersDbRepository) {

    suspend operator fun invoke(nickname: String): Boolean {
        return withContext(Dispatchers.IO) {
            repository.checkingIsUserRegistered(nickname)
        }
    }
}