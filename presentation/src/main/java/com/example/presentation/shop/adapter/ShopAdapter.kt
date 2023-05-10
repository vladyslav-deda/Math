package com.example.presentation.shop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.domain.shop.model.ShopItem
import com.example.presentation.R

class ShopAdapter(
    private val onItemClick: (shopItem: ShopItem) -> Unit
) : ListAdapter<ShopItem, ShopViewHolder>((ShopDiffUtil())) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shop_item, parent, false)
        return ShopViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

private class ShopDiffUtil : DiffUtil.ItemCallback<ShopItem>() {
    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem.icon == newItem.icon
    }

    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem == newItem
    }
}