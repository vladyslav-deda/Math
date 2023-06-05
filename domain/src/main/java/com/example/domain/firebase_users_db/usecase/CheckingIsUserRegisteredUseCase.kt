package com.example.domain.firebase_users_db.usecase

import com.example.domain.firebase_users_db.FirebaseUsersDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CheckingIsUserRegisteredUseCase(private val repository: FirebaseUsersDbRepository) {

    suspend operator fun invoke(
        nickname: String, onSuccess: (isUserHasBeenRegistered: Boolean) -> Unit, onError: () -> Unit
    ) {
        withContext(Dispatchers.IO) {
            repository.checkingIsUserRegistered(nickname, onSuccess, onError)
        }
    }
}