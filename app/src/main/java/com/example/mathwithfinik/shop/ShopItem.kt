package com.example.mathwithfinik.shop

import android.graphics.drawable.Drawable

data class ShopItem (
    val icon: Drawable,
    val name: String,
    val price: Int,
    val isBought: Boolean
)