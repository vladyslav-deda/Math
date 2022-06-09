package com.example.mathwithfinik.shop

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    companion object {
        fun newInstance() = ShopFragment()
    }


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
        items.addAll(getAll())
        var adapterMenu = context?.let { ShopAdapter(items, it) }
        val item2 = ShopItemDb(
            R.drawable.crab,
            "Frog",
            625
        )
        val item4 = ShopItemDb(
            R.drawable.elephant,
            "Frog",
            450
        )
        val item5 = ShopItemDb(
            R.drawable.finik,
            "Frog",
            450
        )
        val item6 = ShopItemDb(
            R.drawable.giraffe,
            "Frog",
            450
        )
        val item7 = ShopItemDb(
            R.drawable.gybka_bob,
            "Frog",
            450
        )
        val item8 = ShopItemDb(
            R.drawable.hero,
            "Frog",
            450
        )
        val item9 = ShopItemDb(
            R.drawable.lion,
            "Frog",
            450
        )
        val item10 = ShopItemDb(
            R.drawable.lion2,
            "Frog",
            450
        )
        val item11 = ShopItemDb(
            R.drawable.lion3,
            "Frog",
            450
        )
        val item12 = ShopItemDb(
            R.drawable.monkey,
            "Frog",
            450
        )
        val item13 = ShopItemDb(
            R.drawable.patrick,
            "Frog",
            450
        )
        val item14 = ShopItemDb(
            R.drawable.pig,
            "Frog",
            450
        )
        val item15 = ShopItemDb(
            R.drawable.rhinocerous,
            "Frog",
            450
        )
        val item16 = ShopItemDb(
            R.drawable.snail,
            "Frog",
            450
        )
        val item17 = ShopItemDb(
            R.drawable.svunka_pepa,
            "Frog",
            450
        )
        val item18 = ShopItemDb(
            R.drawable.zebra,
            "Frog",
            450
        )
//        deleteAllItems()
//        lifecycleScope.launch {
//            ShopRepository(requireContext()).insertShopItem(item2)
//            ShopRepository(requireContext()).insertShopItem(item4)
//            ShopRepository(requireContext()).insertShopItem(item5)
//            ShopRepository(requireContext()).insertShopItem(item6)
//            ShopRepository(requireContext()).insertShopItem(item8)
//            ShopRepository(requireContext()).insertShopItem(item9)
//            ShopRepository(requireContext()).insertShopItem(item10)
//            ShopRepository(requireContext()).insertShopItem(item11)
//            ShopRepository(requireContext()).insertShopItem(item12)
//            ShopRepository(requireContext()).insertShopItem(item13)
//            ShopRepository(requireContext()).insertShopItem(item14)
//            ShopRepository(requireContext()).insertShopItem(item15)
//            ShopRepository(requireContext()).insertShopItem(item16)
//            ShopRepository(requireContext()).insertShopItem(item17)
//            ShopRepository(requireContext()).insertShopItem(item18)
//            adapterMenu?.updateList(getAll())
//        }
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(activity, 2, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterMenu
        }

        binding.moneyBalance.text = balance.toString()

        adapterMenu?.onItemClick = { shopItem ->
            when {
                shopItem.isBought -> {
                    shopItem.itemId?.let { getDB().setItemSelectedTrue(it) }
                    adapterMenu?.updateList(getAll())
                }
                shopItem.isSelected -> {
                    //nothing to do
                }
                else -> {
                    shopItem.itemId?.let { getDB().setIsBought(it) }
                    adapterMenu?.updateList(getAll())
                }
            }
            val l = getAll()
            val o = 0
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShopViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun getDB() = ShopRepository(requireContext())

    fun deleteAllItems() = getDB().deleteAllItems()

    fun getAll() = getDB().getAllShopItems()

}