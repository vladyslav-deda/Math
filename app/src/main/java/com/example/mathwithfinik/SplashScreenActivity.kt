package com.example.mathwithfinik

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.mathwithfinik.databinding.ActivitySplashScreenBinding
import com.example.mathwithfinik.room_db.ShopRepository
import com.example.mathwithfinik.shop.ShopItemDb
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private val binding: ActivitySplashScreenBinding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    private val sharedPref: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(Constants.SHARED_PREFS_NAME, MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        lifecycleScope.launch {
//            ShopRepository(context = baseContext).deleteAllItems()
            if (ShopRepository(context = baseContext).getAllShopItems().isEmpty()) {
                initDB()
            }
        }
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 5000
        anim.fillAfter = true
        Constants.isPremium = false
        binding.ssTvMatWithFinik.startAnimation(anim)
        binding.ssImageFinik.startAnimation(anim)
        Glide
            .with(this)
            .load(ShopRepository(baseContext).getSelected().icon)
            .into(binding.ssImageFinik)
        Handler().postDelayed({
//            val intent: Intent =
//                if (sharedPref.contains(getString(R.string.shared_pref_user_name))) {
//                    Intent(this, MainActivity::class.java)
//                } else {
//                    Intent(this, LoginActivity::class.java)
//                }
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra(Constants.FIRST_TIME, true)
            }
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                binding.ssImageFinik,
                "image_finik"
            )
            startActivity(intent, options.toBundle())
        }, 5000)
    }

    private fun initDB() {
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
            450,
            isBought = true,
            isSelected = true
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
        lifecycleScope.launch {
            ShopRepository(baseContext).insertShopItem(item5)
            ShopRepository(baseContext).insertShopItem(item2)
            ShopRepository(baseContext).insertShopItem(item4)
            ShopRepository(baseContext).insertShopItem(item6)
            ShopRepository(baseContext).insertShopItem(item8)
            ShopRepository(baseContext).insertShopItem(item10)
            ShopRepository(baseContext).insertShopItem(item11)
            ShopRepository(baseContext).insertShopItem(item12)
            ShopRepository(baseContext).insertShopItem(item13)
            ShopRepository(baseContext).insertShopItem(item14)
            ShopRepository(baseContext).insertShopItem(item15)
            ShopRepository(baseContext).insertShopItem(item16)
            ShopRepository(baseContext).insertShopItem(item17)
            ShopRepository(baseContext).insertShopItem(item18)
        }
    }
}