package com.example.domain.shop

import com.example.domain.shop.model.ShopDataTest

interface ShopRepository {

    fun insertShopItem(shopItem: ShopDataTest)

    fun getAllShopItems(): List<ShopDataTest>

    fun updateShopItem(shopItem: ShopDataTest)

    fun deleteShopItem(shopItem: ShopDataTest)

    fun deleteAllItems()

    fun setIsBought(id: Int)

    fun getSelectedItem(): ShopDataTest

    fun setItemSelectedTrue(id: Int)
}