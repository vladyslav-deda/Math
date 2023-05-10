package com.example.presentation.shop.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.shop.model.ShopItem
import com.example.presentation.databinding.ShopItemBinding

class ShopViewHolder(
    itemView: View,
    private val onItemClick: (shopItem: ShopItem) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val binding = ShopItemBinding.bind(itemView)

    fun bind(shopItem: ShopItem) {
        binding.apply {
            shopItem.icon?.let {
                image.setImageDrawable(ContextCompat.getDrawable(itemView.context, it))
            }
            price.text = when {
                shopItem.isSelected == true -> "ОБРАНО"
                shopItem.isBought == true -> "КУПЛЕНО"
                else -> shopItem.price.toString()
            }
            moneyImage.visibility = when {
                shopItem.isSelected == true || shopItem.isBought == true -> View.GONE
                else -> View.VISIBLE
            }

        }
        itemView.setOnClickListener {
            onItemClick(shopItem)
        }
    }
}