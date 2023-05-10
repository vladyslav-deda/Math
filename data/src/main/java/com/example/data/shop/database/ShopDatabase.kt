package com.example.data.shop.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.shop.data.ShopDao
import com.example.domain.shop.model.ShopDataTest

@Database(entities = [ShopDataTest::class], version = 1, exportSchema = false)
abstract class ShopDatabase : RoomDatabase() {

    abstract fun shopDao(): ShopDao
}