package com.example.data.shop.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.domain.shop.model.ShopDataTest

@Dao
interface ShopDao {

    @Insert
    fun insertShopItem(shopItemsDb: ShopDataTest)

    @Query("Select * from shop")
    fun getAllShopItems(): List<ShopDataTest>

    @Update
    fun updateShopItem(shopItemsDb: ShopDataTest)

    @Delete
    fun deleteShopItem(shopItemsDb: ShopDataTest)

    @Query("Delete from shop")
    fun deleteAllItems()

    @Query("Update shop set isBought = 1 where itemId = :id")
    fun setIsBought(id: Int)

    @Query("Select * from shop where isSelected = 1")
    fun getSelectedItem(): ShopDataTest

    @Query("Update shop set isSelected = 1 where itemId = :id")
    fun setItemSelectedTrue(id: Int)

    @Query("Update shop set isSelected = 0")
    fun setAllItemsSelectedFalse()

    @Query("Select * from shop where itemId = :id")
    fun getItemById(id: Int): ShopDataTest
}