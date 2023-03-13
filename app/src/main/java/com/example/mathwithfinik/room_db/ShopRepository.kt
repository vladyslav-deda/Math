package com.example.mathwithfinik.room_db

import android.content.Context
import androidx.room.Query
import com.example.mathwithfinik.shop.ShopItemDb

@Suppress("DEPRECATION")
class ShopRepository(context: Context) {

    var db = ShopDatabase.getInstance(context)?.shopDao()!!

    suspend fun insertShopItem(shopItemDb: ShopItemDb) = db.insertShopItem(shopItemDb)

    suspend fun getAllShopItems(): List<ShopItemDb> = db.getAllShopItems()

    fun updateShopItem(shopItemDb: ShopItemDb) = db.updateShopItem(shopItemDb)

    fun deleteShopItem(shopItemDb: ShopItemDb) = db.deleteShopItem(shopItemDb)

    fun deleteAllItems() = db.deleteAllItems()

    fun setIsBought(id: Int) = db.updateItem(id)

    fun getSelected() = db.getSelectedItem()

    fun setItemSelectedTrue(id: Int) {
        db.setAllItemsSelectedFalse()
        db.setItemSelectedTrue(id)
    }
}