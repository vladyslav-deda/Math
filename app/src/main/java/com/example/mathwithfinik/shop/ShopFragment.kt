package com.example.mathwithfinik.shop

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mathwithfinik.Constants
import com.example.mathwithfinik.R
import com.example.mathwithfinik.databinding.ShopFragmentBinding
import com.example.mathwithfinik.room_db.ShopRepository
import kotlinx.coroutines.launch


class ShopFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferences
    private var balance = 0
    lateinit var binding: ShopFragmentBinding
    private lateinit var viewModel: ShopViewModel
    private val items = ArrayList<ShopItem>()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPref = context.getSharedPreferences(
            Constants.SHARED_PREFS_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
        balance = sharedPref.getInt(Constants.BALANCE, 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ShopFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables", "NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val items = ArrayList<ShopItemDb>()
        lifecycleScope.launch {
            items.addAll(getAll())
        }
        val adapterMenu = context?.let { ShopAdapter(items, it) }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(activity, 2, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterMenu
        }

        binding.moneyBalance.text = balance.toString()

        adapterMenu?.onItemClick = { shopItem ->
            when {
                shopItem.isBought -> {
                    lifecycleScope.launch {
                        shopItem.itemId?.let { getDB().setItemSelectedTrue(it) }
                        adapterMenu?.updateList(getAll())
                    }
                }
                shopItem.isSelected -> {
                    //nothing to do
                }
                else -> {
                    if (balance >= shopItem.price) {
                        balance -= shopItem.price
                        sharedPref.edit().putInt(Constants.BALANCE, balance).apply()
                        lifecycleScope.launch {
                            shopItem.itemId?.let { getDB().setIsBought(it) }
                            adapterMenu?.updateList(getAll())
                        }
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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShopViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private suspend fun getDB() = ShopRepository(requireContext())

    private suspend fun deleteAllItems() = getDB().deleteAllItems()

    private suspend fun getAll() = getDB().getAllShopItems()
}