package com.example.data.shop

import com.example.data.shop.data.ShopDao
import com.example.domain.shop.model.ShopDataTest
import com.example.domain.shop.ShopRepository
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val shopDao: ShopDao
) : ShopRepository {

    override fun insertShopItem(shopItem: ShopDataTest) = shopDao.insertShopItem(shopItem)

    override fun getAllShopItems(): List<ShopDataTest> = shopDao.getAllShopItems()

    override fun updateShopItem(shopItem: ShopDataTest) = shopDao.updateShopItem(shopItem)

    override fun deleteShopItem(shopItem: ShopDataTest) = shopDao.deleteShopItem(shopItem)

    override fun deleteAllItems() = shopDao.deleteAllItems()

    override fun setIsBought(id: Int) {
        shopDao.setIsBought(id)
    }

    override fun getSelectedItem() = shopDao.getSelectedItem()

    override fun setItemSelectedTrue(id: Int) {
        shopDao.setAllItemsSelectedFalse()
        shopDao.setItemSelectedTrue(id)
    }
}