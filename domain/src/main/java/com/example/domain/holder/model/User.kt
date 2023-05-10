package com.example.domain.holder.model

import com.example.domain.shop.model.ShopItem

data class User(
    val userName: String? = null,
    val password: String? = null,
    var moneyBalance: Int? = null,
    val level: String? = null,
    var shopItems: List<ShopItem>? = null
)
