package com.example.data.shop.di

import com.example.data.shop.ShopRepositoryImpl
import com.example.data.shop.data.ShopDao
import com.example.domain.shop.ShopRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ShopModule {

    @ViewModelScoped
    @Provides
    fun provideShopRepository(shopDao: ShopDao): ShopRepository = ShopRepositoryImpl(shopDao)
}