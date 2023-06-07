package com.example.data.firebase_tasks_db

import com.example.data.firebase_tasks_db.di.TASKS_DB_NAME
import com.example.domain.firebase_tasks_db.FirebaseTasksDbRepository
import com.example.domain.firebase_tasks_db.model.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject
import javax.inject.Named

class FirebaseTasksDbRepositoryImpl @Inject constructor(
    @Named(TASKS_DB_NAME) val tasksDbReference: DatabaseReference
) : FirebaseTasksDbRepository {

    override suspend fun loadTasks(
        onSuccess: (dataSnapshot: DataSnapshot) -> Unit,
        onError: () -> Unit
    ) {
        tasksDbReference.get().addOnCompleteListener {
            if (it.isSuccessful) {
                onSuccess(it.result)
            } else {
                onError()
            }
        }
    }

    override suspend fun addTask(
        textOfTask: String,
        answerOfTask: Int,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        tasksDbReference.push()
            .setValue(Task(textOfTask, answerOfTask))
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onSuccess()
                } else {
                    onError()
                }
            }
    }
}