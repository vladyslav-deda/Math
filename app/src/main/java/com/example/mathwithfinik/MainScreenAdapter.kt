package com.example.mathwithfinik

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mathwithfinik.models.MenuItem

class MainScreenAdapter(private val menuItems: ArrayList<MenuItem>) :
    RecyclerView.Adapter<MainScreenAdapter.MainScreenViewHolder>() {
    var onItemClick: ((MenuItem) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainScreenViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.msf_item, parent, false)
        return MainScreenViewHolder(view)
    }

    override fun getItemCount(): Int = menuItems.size

    override fun onBindViewHolder(holder: MainScreenViewHolder, position: Int) {
        val item = menuItems[position]
        if (false) {
            holder.msfItemImage.setImageDrawable(item.icon)
            holder.msfNameTv.text = "Функціонал \"${item.name}\" наразі у розробці"
            holder.msfNameTv.textSize = 14f
        } else {
            holder.msfItemImage.setImageDrawable(item.icon)
            holder.msfNameTv.text = item.name
        }

    }

    inner class MainScreenViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val msfItemImage: ImageView = v.findViewById(R.id.msf_item_image)
        val msfNameTv: TextView = v.findViewById(R.id.msf_tv)

        init {
            v.setOnClickListener {
                onItemClick?.invoke(menuItems[adapterPosition])
            }
        }

    }
}
