package com.example.presentation.shop.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.shop.model.ShopItem
import com.example.presentation.R
import com.example.presentation.base.extension.getImageRes
import com.example.presentation.databinding.ShopItemBinding

class ShopViewHolder(
    itemView: View,
    private val onItemClick: (shopItem: ShopItem) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val binding = ShopItemBinding.bind(itemView)

    fun bind(shopItem: ShopItem) {
        binding.apply {
            image.setImageDrawable(
                ContextCompat.getDrawable(
                    itemView.context,
                    shopItem.getImageRes()
                )
            )

            price.text = when {
                shopItem.isSelected == true -> itemView.context.getString(R.string.selected)
                shopItem.isBought == true -> itemView.context.getString(R.string.bought)
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