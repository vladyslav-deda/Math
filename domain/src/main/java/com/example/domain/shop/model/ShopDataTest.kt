package com.example.domain.shop.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop")
data class ShopDataTest(
    val icon: Int,
    val price: Int,
    var isBought: Boolean = false,
    var isSelected: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    var itemId: Int? = null
}