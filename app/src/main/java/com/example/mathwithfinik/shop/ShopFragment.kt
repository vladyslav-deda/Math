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

        val item = ShopItemDb(
            R.drawable.lion,
            "Lion",
            400
        )
        val item2 = ShopItemDb(
            R.drawable.frog,
            "Frog",
            625
        )
        val item3 = ShopItemDb(
            R.drawable.finik,
            "Frog",
            450,
            isBought = true
        )
        deleteAllItems()
        lifecycleScope.launch {
            ShopRepository(requireContext()).insertShopItem(item3)
            ShopRepository(requireContext()).insertShopItem(item2)
            ShopRepository(requireContext()).insertShopItem(item)
            ShopRepository(requireContext()).insertShopItem(item2)
            ShopRepository(requireContext()).insertShopItem(item)
            ShopRepository(requireContext()).insertShopItem(item2)
            adapterMenu?.updateList(getAll())
        }
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(activity, 2, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterMenu
        }

        binding.moneyBalance.text = balance.toString()

        adapterMenu?.onItemClick = { item ->
            item.itemId?.let { getDB().setIsBought(it) }
            adapterMenu?.updateList(getAll())
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