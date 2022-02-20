package com.example.mathwithfinik

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.mathwithfinik.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val sharedPref: SharedPreferences.Editor by lazy {
        applicationContext.getSharedPreferences(Constants.SHARED_PREFS_NAME, MODE_PRIVATE)
            .edit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        findViewById<EditText>(R.id.la_et_name).doOnTextChanged { _, _, _, _ ->
            findViewById<Button>(R.id.la_button_continue).visibility = View.VISIBLE
        }
        findViewById<Button>(R.id.la_button_continue).setOnClickListener {
            sharedPref.putString(
                getString(R.string.shared_pref_user_name),
                findViewById<EditText>(R.id.la_et_name).text.toString()
            ).apply()
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}