package com.example.mathwithfinik

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.mathwithfinik.databinding.ActivityMainBinding
import com.example.mathwithfinik.ui.mainscreen.FirstSpeachDialog
import com.example.mathwithfinik.ui.mainscreen.MainScreenFragment

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainScreenFragment.newInstance())
                .commitNow()
        }
        if (intent.getBooleanExtra(Constants.FIRST_TIME, false)) {
            FirstSpeachDialog.showDialog(this)
        }
    }
}