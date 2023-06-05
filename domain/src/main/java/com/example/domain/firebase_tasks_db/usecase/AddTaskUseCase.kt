package com.example.domain.firebase_tasks_db.usecase

import com.example.domain.firebase_tasks_db.FirebaseTasksDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddTaskUseCase(private val repository: FirebaseTasksDbRepository) {

    suspend operator fun invoke(
        textOfTask: String,
        answerOfTask: Int,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        withContext(Dispatchers.IO) {
            repository.addTask(textOfTask, answerOfTask, onSuccess, onError)
        }
    }
}