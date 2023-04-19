package com.example.presentation.splash.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.shop.ShopRepository
import com.example.domain.shop.model.ShopItem
import com.example.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: ShopRepository
) : ViewModel() {

    init {
        val items = repository.getAllShopItems()
        if (items.isEmpty()) {
            dummyListOfShopItems().forEach {
                repository.insertShopItem(it)
            }
        }
    }

    fun getSelectedItem(): ShopItem = repository.getSelectedItem()

    private fun dummyListOfShopItems(): List<ShopItem> {
        return listOf(
            ShopItem(
                icon = R.drawable.finik,
                price = 450,
                isBought = true,
                isSelected = true
            ),
            ShopItem(
                icon = R.drawable.crab,
                price = 625
            ), ShopItem(
                icon = R.drawable.elephant,
                price = 450
            ),
            ShopItem(
                icon = R.drawable.giraffe,
                price = 450
            ),
            ShopItem(
                icon = R.drawable.gybka_bob,
                price = 450
            ),
            ShopItem(
                icon = R.drawable.hero,
                price = 450
            ),
            ShopItem(
                icon = R.drawable.lion2,
                price = 450
            ),
            ShopItem(
                icon = R.drawable.lion3,
                price = 450
            ),
            ShopItem(
                icon = R.drawable.monkey,
                price = 450
            ),
            ShopItem(
                icon = R.drawable.patrick,
                price = 450
            ),
            ShopItem(
                icon = R.drawable.pig,
                price = 450
            ),
            ShopItem(
                icon = R.drawable.rhinocerous,
                price = 450
            ),
            ShopItem(
                icon = R.drawable.snail,
                price = 450
            ),
            ShopItem(
                icon = R.drawable.svunka_pepa,
                price = 450
            ),
            ShopItem(
                icon = R.drawable.zebra,
                price = 450
            )
        )
    }
}