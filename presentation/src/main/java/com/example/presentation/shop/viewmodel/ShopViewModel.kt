package com.example.presentation.shop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.holder.SessionHolder
import com.example.domain.shop.model.ShopItem

class ShopViewModel: ViewModel() {

    private val _shopItems = MutableLiveData<List<ShopItem>>()
    val shopItems: LiveData<List<ShopItem>> = _shopItems

    fun updateItems() {
        SessionHolder.currentUser?.shopItems?.let {
            _shopItems.postValue(it)
        }
    }
}