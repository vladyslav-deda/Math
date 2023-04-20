package com.example.data.moneybalance.di

import android.content.SharedPreferences
import com.example.data.moneybalance.BalanceRepositoryImpl
import com.example.domain.moneybalance.BalanceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object BalanceModule {

    @ViewModelScoped
    @Provides
    fun provideBalanceRepository(sharedPreferences: SharedPreferences): BalanceRepository = BalanceRepositoryImpl(sharedPreferences)
}