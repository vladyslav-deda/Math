package com.example.data.shop

import com.example.data.shop.data.ShopDao
import com.example.domain.shop.ShopRepository
import com.example.domain.shop.model.ShopItem

class ShopRepositoryImpl (
    private val shopDao: ShopDao
) : ShopRepository {

    override fun insertShopItem(shopItem: ShopItem) = shopDao.insertShopItem(shopItem)

    override fun getAllShopItems(): List<ShopItem> = shopDao.getAllShopItems()

    override fun updateShopItem(shopItem: ShopItem) = shopDao.updateShopItem(shopItem)

    override fun deleteShopItem(shopItem: ShopItem) = shopDao.deleteShopItem(shopItem)

    override fun deleteAllItems() = shopDao.deleteAllItems()

    override fun setIsBought(id: Int) = shopDao.updateItem(id)

    override fun getSelectedItem() = shopDao.getSelectedItem()

    override fun setItemSelectedTrue(id: Int) {
        shopDao.setAllItemsSelectedFalse()
        shopDao.setItemSelectedTrue(id)
    }
}