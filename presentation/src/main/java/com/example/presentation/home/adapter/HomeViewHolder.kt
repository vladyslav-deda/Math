package com.example.presentation.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.databinding.HomeItemBinding
import com.example.presentation.home.model.MenuItem

class HomeViewHolder(
    itemView: View,
    private val onItemClick: (menuItem: MenuItem) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val binding = HomeItemBinding.bind(itemView)

    fun bind(menuItem: MenuItem) {
        binding.apply {
            itemImage.setImageDrawable(menuItem.icon)
            tvItem.text = menuItem.name
        }
        itemView.setOnClickListener {
            onItemClick(menuItem)
        }
    }
}