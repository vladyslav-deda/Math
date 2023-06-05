package com.example.domain.firebase_users_db.usecase

import com.example.domain.firebase_users_db.FirebaseUsersDbRepository
import com.google.firebase.database.DataSnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthUserUseCase(private val repository: FirebaseUsersDbRepository) {

    suspend operator fun invoke(
        nickname: String,
        onSuccess: (dataSnapshot: DataSnapshot) -> Unit,
        onError: () -> Unit
    ) {
        withContext(Dispatchers.IO) {
            repository.authUser(nickname, onSuccess, onError)
        }
    }
}