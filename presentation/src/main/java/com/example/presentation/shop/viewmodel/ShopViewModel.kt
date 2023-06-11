package com.example.presentation.shop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.firebase_users_db.usecase.UpdateMoneyBalanceUseCase
import com.example.domain.firebase_users_db.usecase.UpdateShopValuesUseCase
import com.example.domain.holder.SessionHolder
import com.example.domain.shop.model.ShopItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val updateMoneyBalanceUseCase: UpdateMoneyBalanceUseCase,
    private val updateShopValuesUseCase: UpdateShopValuesUseCase
) : ViewModel() {

    private val _shopItems = MutableLiveData<List<ShopItem>>()
    val shopItems: LiveData<List<ShopItem>> = _shopItems

    fun updateStatusOfShopItem(
        shopItem: ShopItem,
        setItemStatusAsBought: Boolean = false,
        setItemStatusAsSelected: Boolean = false
    ) {
        val shopItems = ArrayList(SessionHolder.currentUser?.shopItems ?: emptyList())

        val indexOfNewSelectedItem = shopItems.indexOf(shopItem)
        var newShopItem = ShopItem()
        val newShopItemForOldPlace: ShopItem
        val oldSelectedItem = shopItems.firstOrNull { item ->
            item.isSelected
        }
        val indexOfOldSelectedItem = shopItems.indexOf(oldSelectedItem)
        if (setItemStatusAsBought) {
            newShopItem = ShopItem(
                shopItems[indexOfNewSelectedItem].id,
                shopItems[indexOfNewSelectedItem].price,
                true,
                false
            )
        } else if (setItemStatusAsSelected) {
            newShopItemForOldPlace = ShopItem(
                shopItems[indexOfOldSelectedItem].id,
                shopItems[indexOfOldSelectedItem].price,
                true,
                false
            )
            newShopItem = ShopItem(
                shopItems[indexOfNewSelectedItem].id,
                shopItems[indexOfNewSelectedItem].price,
                true,
                true
            )
            shopItems[indexOfOldSelectedItem] = newShopItemForOldPlace
        }
        shopItems[indexOfNewSelectedItem] = newShopItem
        SessionHolder.currentUser?.let { user ->
            user.shopItems = shopItems
            user.userName?.let { userName ->
                viewModelScope.launch {
                    updateShopValuesUseCase.invoke(userName, shopItems)
                }
            }
            user.shopItems?.let {
                _shopItems.postValue(it)
            }
        }
    }

    fun updateMoneyBalance(
        newBalance: Int,
    ) {
        SessionHolder.currentUser?.let {
            it.userName?.let {
                viewModelScope.launch {
                    updateMoneyBalanceUseCase.invoke(it, newBalance)
                }
            }
            it.moneyBalance = newBalance
        }
    }
}