package com.example.data.firebase_tasks_db.di

import com.example.data.firebase_tasks_db.FirebaseTasksDbRepositoryImpl
import com.example.domain.firebase_tasks_db.FirebaseTasksDbRepository
import com.example.domain.firebase_tasks_db.usecase.AddTaskUseCase
import com.example.domain.firebase_tasks_db.usecase.LoadTasksUseCase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Named

const val TASKS_DB_NAME = "Tasks"

@Module
@InstallIn(ViewModelComponent::class)
object FirebaseTasksDbModule {

    @Provides
    @Named(TASKS_DB_NAME)
    fun provideTasksDbReference(firebaseDatabase: FirebaseDatabase): DatabaseReference =
        firebaseDatabase.getReference(TASKS_DB_NAME)

    @Provides
    fun provideFirebaseRepository(
        @Named(TASKS_DB_NAME) usersDbReference: DatabaseReference
    ): FirebaseTasksDbRepository = FirebaseTasksDbRepositoryImpl(usersDbReference)

    @Provides
    fun provideLoadTasksUseCase(repository: FirebaseTasksDbRepository) =
        LoadTasksUseCase(repository)

    @Provides
    fun provideAddTaskUseCase(repository: FirebaseTasksDbRepository) =
        AddTaskUseCase(repository)
}