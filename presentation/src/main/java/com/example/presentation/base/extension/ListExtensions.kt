package com.example.presentation.base.extension

import com.example.domain.shop.model.ShopItem
import com.example.presentation.Constants
import com.example.presentation.R

fun List<ShopItem>.getSelectedItem(): ShopItem? {
    return this.firstOrNull {
        it.isSelected
    }
}

fun ShopItem.getImageRes(): Int {
    return try {
        this.id.let { Constants.shopImagesList[it] }
    } catch (e: Exception) {
        R.drawable.logo_cat
    }
}
