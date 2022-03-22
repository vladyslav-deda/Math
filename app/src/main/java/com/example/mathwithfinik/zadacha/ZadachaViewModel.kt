package com.example.mathwithfinik.zadacha

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mathwithfinik.Constants
import com.example.mathwithfinik.R

class ZadachaViewModel : ViewModel() {

    val level = MutableLiveData<String>()

    fun showDialog(context: Context?) {
        val dialog = context?.let { Dialog(it) }
        dialog?.let { dlg ->
            dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dlg.setCancelable(false);
            dlg.setContentView(R.layout.dialog_level_layout)
            dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dlg.findViewById<Button>(R.id.button_hard).setOnClickListener {
                level.value = Constants.HARD_CHAR
                dlg.dismiss()
            }
            dlg.findViewById<Button>(R.id.button_medium).setOnClickListener {
                level.value = Constants.MEDIUM_CHAR
                dlg.dismiss()
            }
            dlg.findViewById<Button>(R.id.button_easy).setOnClickListener {
                level.value = Constants.EASY_CHAR
                dlg.dismiss()
            }
            dlg.show()
        }
    }



    fun getFileName(level: String?): String = if (Constants.isPremium) {
        when (level) {
            Constants.EASY_CHAR -> PREMIUM_ZADACHA_EASY
            Constants.MEDIUM_CHAR -> PREMIUM_ZADACHA_MEDIUM
            Constants.HARD_CHAR -> PREMIUM_ZADACHA_HARD
            else -> PREMIUM_ZADACHA_MEDIUM
        }
    } else {
        when (level) {
            Constants.EASY_CHAR -> FREE_ZADACHA_EASY
            Constants.MEDIUM_CHAR -> FREE_ZADACHA_MEDIUM
            Constants.HARD_CHAR -> FREE_ZADACHA_HARD
            else -> FREE_ZADACHA_MEDIUM
        }
    }

    companion object {
        const val FREE_ZADACHA_EASY = "free_zadachi_easy.txt"
        const val FREE_ZADACHA_MEDIUM = "free_zadachi_easy.txt"
        const val FREE_ZADACHA_HARD = "free_zadachi_easy.txt"
        const val PREMIUM_ZADACHA_EASY = "free_zadachi_easy"
        const val PREMIUM_ZADACHA_MEDIUM = "free_zadachi_easy"
        const val PREMIUM_ZADACHA_HARD = "free_zadachi_easy"
    }
}