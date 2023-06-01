package com.example.presentation.base

import com.example.domain.shop.model.ShopItem
import com.example.presentation.Constants
import com.example.presentation.R

fun List<ShopItem>.getSelectedItem(): ShopItem? {
    return this.firstOrNull {
        it.isSelected == true
    }
}

fun ShopItem.getImageRes(): Int {
    return try {
        this.id?.let { Constants.shopImagesList[it] } ?: R.drawable.logo_cat
    } catch (e: Exception) {
        R.drawable.logo_cat
    }
}
