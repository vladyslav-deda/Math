package com.example.mathwithfinik.room_db

import androidx.room.*
import com.example.mathwithfinik.shop.ShopItem
import com.example.mathwithfinik.shop.ShopItemDb

@Dao
interface ShopDao {
    @Insert
    fun insertShopItem(shopItemsDb: ShopItemDb)

    @Query("Select * from shop")
    fun getAllShopItems(): List<ShopItemDb>

    @Update
    fun updateShopItem(shopItemsDb: ShopItemDb)

    @Delete
    fun deleteShopItem(shopItemsDb: ShopItemDb)

    @Query("Delete from shop")
    fun deleteAllItems()

    @Query("Update shop set isBought = 1 where itemId = :id")
    fun updateItem(id: Int)

    @Query("Select * from shop where isSelected = 1")
    fun getSelectedItem(): ShopItemDb

    @Query("Update shop set isSelected = 1 where itemId = :id")
    fun setItemSelectedTrue(id: Int)

    @Query("Update shop set isSelected = 0")
    fun setAllItemsSelectedFalse()
}