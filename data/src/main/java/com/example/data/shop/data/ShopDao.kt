package com.example.data.shop.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.domain.shop.model.ShopItem

@Dao
interface ShopDao {

    @Insert
    fun insertShopItem(shopItemsDb: ShopItem)

    @Query("Select * from shop")
    fun getAllShopItems(): List<ShopItem>

    @Update
    fun updateShopItem(shopItemsDb: ShopItem)

    @Delete
    fun deleteShopItem(shopItemsDb: ShopItem)

    @Query("Delete from shop")
    fun deleteAllItems()

    @Query("Update shop set isBought = 1 where itemId = :id")
    fun updateItem(id: Int)

    @Query("Select * from shop where isSelected = 1")
    fun getSelectedItem(): ShopItem

    @Query("Update shop set isSelected = 1 where itemId = :id")
    fun setItemSelectedTrue(id: Int)

    @Query("Update shop set isSelected = 0")
    fun setAllItemsSelectedFalse()
}