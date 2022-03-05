package com.example.mathwithfinik.models

import android.graphics.drawable.Drawable

data class MenuItem(val icon: Drawable, val name: String, val blocked: Boolean? = false)
