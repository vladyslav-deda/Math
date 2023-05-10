package com.example.presentation.shop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.holder.SessionHolder
import com.example.domain.shop.ShopRepository
import com.example.domain.shop.model.ShopItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class ShopViewModel @Inject constructor(
    private val shopRepository: ShopRepository
) : ViewModel() {

    private val _shopItems = MutableLiveData<List<ShopItem>>()
    val shopItems: LiveData<List<ShopItem>> = _shopItems

    fun setItemSelectedTrue(item: ShopItem) {
//        item.itemId?.let {
//            shopRepository.setItemSelectedTrue(it)
//        }
    }

    fun getAllItems() = emptyList<ShopItem>()

    fun setIsBought(item: ShopItem) {
//        item.itemId?.let {
//            shopRepository.setIsBought(it)
//        }
    }

    fun updateItems() {
        SessionHolder.currentUser?.shopItems?.let {
            _shopItems.postValue(it)
        }
    }
}