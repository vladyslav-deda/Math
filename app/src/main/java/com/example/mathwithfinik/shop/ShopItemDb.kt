package com.example.mathwithfinik.shop

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop")
data class ShopItemDb(
    val icon: Int,
    val name: String,
    val price: Int,
    var isBought: Boolean = false,
    var isSelected: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    var itemId: Int? = null
}