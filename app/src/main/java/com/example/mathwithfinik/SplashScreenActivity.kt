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
import com.bumptech.glide.Glide
import com.example.mathwithfinik.databinding.ActivitySplashScreenBinding
import com.example.mathwithfinik.room_db.ShopRepository

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
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 5000
        anim.fillAfter = true
        Constants.isPremium = false
        binding.ssTvMatWithFinik.startAnimation(anim)
        binding.ssImageFinik.startAnimation(anim)
//        Glide
//            .with(this)
//            .load(ShopRepository(baseContext).getSelected().icon)
//            .into(binding.ssImageFinik)
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
}