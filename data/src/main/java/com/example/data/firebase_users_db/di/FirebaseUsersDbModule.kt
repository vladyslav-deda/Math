package com.example.data.firebase_users_db.di

import com.example.data.firebase_users_db.FirebaseUsersDbRepositoryImpl
import com.example.domain.firebase_users_db.FirebaseUsersDbRepository
import com.example.domain.firebase_users_db.usecase.AuthUserUseCase
import com.example.domain.firebase_users_db.usecase.CheckingIsUserRegisteredUseCase
import com.example.domain.firebase_users_db.usecase.RegisterNewUserUseCase
import com.example.domain.firebase_users_db.usecase.UpdateMoneyBalanceUseCase
import com.example.domain.firebase_users_db.usecase.UpdateShopValuesUseCase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Named

const val USERS_DB_NAME = "Users"

@Module
@InstallIn(ViewModelComponent::class)
object FirebaseUsersDbModule {

    @Provides
    @Named(USERS_DB_NAME)
    fun provideUsersDbReference(firebaseDatabase: FirebaseDatabase): DatabaseReference =
        firebaseDatabase.getReference(USERS_DB_NAME)

    @Provides
    fun provideFirebaseRepository(
        @Named(USERS_DB_NAME) usersDbReference: DatabaseReference
    ): FirebaseUsersDbRepository = FirebaseUsersDbRepositoryImpl(usersDbReference)

    @Provides
    fun provideUpdateMoneyBalanceUseCase(firebaseUsersDbRepository: FirebaseUsersDbRepository) =
        UpdateMoneyBalanceUseCase(firebaseUsersDbRepository)

    @Provides
    fun provideRegisterNewUserUseCase(firebaseUsersDbRepository: FirebaseUsersDbRepository) =
        RegisterNewUserUseCase(firebaseUsersDbRepository)

    @Provides
    fun provideCheckingIsUserRegisteredUseCase(firebaseUsersDbRepository: FirebaseUsersDbRepository) =
        CheckingIsUserRegisteredUseCase(firebaseUsersDbRepository)

    @Provides
    fun provideAuthUserUseCase(firebaseUsersDbRepository: FirebaseUsersDbRepository) =
        AuthUserUseCase(firebaseUsersDbRepository)

    @Provides
    fun provideUpdateShopValuesUseCase(firebaseUsersDbRepository: FirebaseUsersDbRepository) =
        UpdateShopValuesUseCase(firebaseUsersDbRepository)
}