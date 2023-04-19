package com.example.mathwithfinik

import android.content.Context
import androidx.room.Room
import com.example.data.shop.data.ShopDao
import com.example.data.shop.repository.ShopDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ShopDatabase::class.java, "shop.db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    @Singleton
    fun provideDao(shopDatabase: ShopDatabase): ShopDao = shopDatabase.shopDao()
}