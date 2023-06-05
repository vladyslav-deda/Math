package com.example.domain.firebase_tasks_db

import com.google.firebase.database.DataSnapshot

interface FirebaseTasksDbRepository {

    suspend fun loadTasks(
        onSuccess: (dataSnapshot: DataSnapshot) -> Unit,
        onError: () -> Unit
    )

    suspend fun addTask(
        textOfTask:String,
        answerOfTask:Int,
        onSuccess: () -> Unit,
        onError: () -> Unit
    )
}