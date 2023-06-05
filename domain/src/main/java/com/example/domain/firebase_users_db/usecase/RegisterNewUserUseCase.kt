package com.example.domain.firebase_users_db.usecase

import com.example.domain.firebase_users_db.FirebaseUsersDbRepository
import com.example.domain.holder.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegisterNewUserUseCase(private val repository: FirebaseUsersDbRepository) {

    suspend operator fun invoke(
        user: User,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        withContext(Dispatchers.IO) {
            repository.registerNewUser(user, onSuccess, onError)
        }
    }
}