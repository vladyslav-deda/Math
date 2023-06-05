package com.example.domain.firebase_tasks_db.usecase

import com.example.domain.firebase_tasks_db.FirebaseTasksDbRepository
import com.google.firebase.database.DataSnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadTasksUseCase(private val repository: FirebaseTasksDbRepository) {

    suspend operator fun invoke(
        onSuccess: (dataSnapshot: DataSnapshot) -> Unit,
        onError: () -> Unit
    ) {
        withContext(Dispatchers.IO) {
            repository.loadTasks(onSuccess, onError)
        }
    }
}