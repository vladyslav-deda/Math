package com.example.presentation.base

import com.example.domain.shop.model.ShopItem

fun List<ShopItem>.getSelectedItem(): ShopItem? {
    return this.firstOrNull {
        it.isSelected == true
    }
}