package com.example.presentation.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.holder.SessionHolder
import com.example.domain.shop.model.ShopItem
import com.example.presentation.Constants
import com.example.presentation.databinding.ShopFragmentBinding
import com.example.presentation.shop.adapter.ShopAdapter
import com.example.presentation.shop.viewmodel.ShopViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopFragment : Fragment() {

    private lateinit var binding: ShopFragmentBinding

    private val viewModel by viewModels<ShopViewModel>()

    private var shopAdapter: ShopAdapter? = null

    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ShopFragmentBinding.inflate(inflater, container, false)
        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.USERS_DB_NAME)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        updateMoneyBalanceOnUI()
        viewModel.shopItems.observe(viewLifecycleOwner) {
            shopAdapter?.submitList(it)
        }
    }

    private fun initRecyclerView() {
        shopAdapter = ShopAdapter {
            when {
                it.isBought == true && it.isSelected == false -> {
                    updateStatusOfShopItem(it, setItemStatusAsSelected = true)
                }

                it.isSelected == true -> {
                    Toast.makeText(
                        context,
                        "Цей герой вже обраний",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    if ((SessionHolder.currentUser?.moneyBalance ?: 0) >= (it.price ?: 0)) {
                        val newBalance = SessionHolder.currentUser?.moneyBalance?.minus(it.price ?: 0) ?: 0
                        updateBalance(newBalance)
                        updateMoneyBalanceOnUI()
                        updateStatusOfShopItem(it, setItemStatusAsBought = true)
                    } else {
                        Toast.makeText(
                            context,
                            "На твоєму балансі недостатньо монеток для покупки",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        binding.shopItems.apply {
            layoutManager = GridLayoutManager(activity, 2, LinearLayoutManager.HORIZONTAL, false)
            adapter = shopAdapter
        }
        SessionHolder.currentUser?.shopItems?.let {
            shopAdapter?.submitList(it)
        }
    }

    private fun updateStatusOfShopItem(
        shopItem: ShopItem,
        setItemStatusAsBought: Boolean = false,
        setItemStatusAsSelected: Boolean = false
    ) {
        val shopItems = ArrayList(SessionHolder.currentUser?.shopItems ?: emptyList())

        val indexOfNewSelectedItem = shopItems.indexOf(shopItem)
        var newShopItem = ShopItem()
        val newShopItemForOldPlace: ShopItem
        val oldSelectedItem = shopItems.firstOrNull { item ->
            item.isSelected == true
        }
        val indexOfOldSelectedItem = shopItems.indexOf(oldSelectedItem)
        if (setItemStatusAsBought) {
            newShopItem = ShopItem(
                id = shopItems[indexOfNewSelectedItem].id,
                price = shopItems[indexOfNewSelectedItem].price,
                isBought = true,
                isSelected = false
            )
        } else if (setItemStatusAsSelected) {
            newShopItemForOldPlace = ShopItem(
                id = shopItems[indexOfOldSelectedItem].id,
                price = shopItems[indexOfOldSelectedItem].price,
                isBought = true,
                isSelected = false
            )
            newShopItem = ShopItem(
                id = shopItems[indexOfNewSelectedItem].id,
                price = shopItems[indexOfNewSelectedItem].price,
                isBought = true,
                isSelected = true
            )
            shopItems[indexOfOldSelectedItem] = newShopItemForOldPlace
        }
        shopItems[indexOfNewSelectedItem] = newShopItem
        SessionHolder.currentUser?.shopItems = shopItems
        SessionHolder.currentUser?.userName?.let { userName ->
            databaseReference.child(userName).child(Constants.SHOP_ITEMS).setValue(shopItems)
        }
        viewModel.updateItems()
    }

    private fun updateMoneyBalanceOnUI() {
        binding.moneyBalance.text = (SessionHolder.currentUser?.moneyBalance ?: 0).toString()
    }

    private fun updateBalance(newBalance: Int) {
        SessionHolder.currentUser?.userName?.let {
            databaseReference.child(it).child(Constants.MONEY_BALANCE).setValue(newBalance)
        }
        SessionHolder.currentUser?.moneyBalance = newBalance
    }
}