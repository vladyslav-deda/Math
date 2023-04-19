package com.example.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.presentation.R
import com.example.presentation.home.model.MenuItem

class HomeAdapter(
    private val onItemClick: (menuItem: MenuItem) -> Unit
) : ListAdapter<MenuItem, HomeViewHolder>((HomeDiffUtil())) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_item, parent, false)
        return HomeViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

private class HomeDiffUtil : DiffUtil.ItemCallback<MenuItem>() {
    override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
        return oldItem.name == newItem.name
    }
}