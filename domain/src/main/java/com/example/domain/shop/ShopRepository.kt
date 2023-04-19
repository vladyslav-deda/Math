package com.example.domain.shop

import com.example.domain.shop.model.ShopItem

interface ShopRepository {

    fun insertShopItem(shopItem: ShopItem)

    fun getAllShopItems(): List<ShopItem>

    fun updateShopItem(shopItem: ShopItem)

    fun deleteShopItem(shopItem: ShopItem)

    fun deleteAllItems()

    fun setIsBought(id: Int)

    fun getSelectedItem(): ShopItem

    fun setItemSelectedTrue(id: Int)
}