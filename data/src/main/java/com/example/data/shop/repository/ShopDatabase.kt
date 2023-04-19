package com.example.data.shop.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.shop.data.ShopDao
import com.example.domain.shop.model.ShopItem

@Database(entities = [ShopItem::class], version = 1, exportSchema = false)
abstract class ShopDatabase : RoomDatabase() {

    abstract fun shopDao(): ShopDao
}