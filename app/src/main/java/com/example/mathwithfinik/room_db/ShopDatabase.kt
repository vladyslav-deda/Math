package com.example.mathwithfinik.room_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mathwithfinik.shop.ShopItemDb

@Database(entities = [ShopItemDb::class], version = 5, exportSchema = false)
abstract class ShopDatabase : RoomDatabase() {
    abstract fun shopDao(): ShopDao

    companion object {
        private var INSTANCE: ShopDatabase? = null

        fun getInstance(context: Context): ShopDatabase? {
            if (INSTANCE == null) {
                synchronized(ShopDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ShopDatabase::class.java, "user.db"
                    ).allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}