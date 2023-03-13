package com.example.mathwithfinik.shop

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mathwithfinik.R

class ShopAdapter(private val shopItemDbs: ArrayList<ShopItemDb>, val context: Context) :
    RecyclerView.Adapter<ShopAdapter.ShopViewHolder>() {

    var onItemClick: ((ShopItemDb) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.shop_item, parent, false)
        return ShopViewHolder(view)
    }

    override fun getItemCount(): Int = shopItemDbs.size

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val item = shopItemDbs[position]
        holder.apply {
            image.setImageDrawable(context.getDrawable(item.icon))
            name.text = item.name
            price.text = when {
                item.isSelected -> "ОБРАНО"
                item.isBought -> "КУПЛЕНО"
                else -> item.price.toString()
            }
//            price.text = item.price.toString()
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(shopItemDbs: List<ShopItemDb>) {
        this.shopItemDbs.clear()
        this.shopItemDbs.addAll(shopItemDbs)
        this.notifyDataSetChanged()
    }

    inner class ShopViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val image: ImageView = v.findViewById(R.id.image)
        val name: TextView = v.findViewById(R.id.name)
        val price: TextView = v.findViewById(R.id.price)

        init {
            v.setOnClickListener {
                onItemClick?.invoke(shopItemDbs[adapterPosition])
            }
        }

    }
}