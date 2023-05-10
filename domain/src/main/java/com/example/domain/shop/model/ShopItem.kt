package com.example.domain.shop.model

data class ShopItem(
    val id: Int? = null,
    val icon: Int? = null,
    val price: Int? = null,
    var isBought: Boolean? = false,
    var isSelected: Boolean? = false
)