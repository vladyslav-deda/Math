package com.example.mathwithfinik

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button

object LevelDialog {

    fun showDialog(context: Context?): String {
        val dialog = context?.let { Dialog(it) }
        var level = "default text"
        dialog?.let { dlg ->
            dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dlg.setCancelable(false);
            dlg.setContentView(R.layout.dialog_level_layout)
            dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dlg.findViewById<Button>(R.id.button_hard).setOnClickListener {
                level = Constants.HARD_CHAR
                dlg.dismiss()
            }
            dlg.findViewById<Button>(R.id.button_medium).setOnClickListener {
                level = Constants.MEDIUM_CHAR
                dlg.dismiss()
            }
            dlg.findViewById<Button>(R.id.button_easy).setOnClickListener {
                level = Constants.EASY_CHAR
                dlg.dismiss()
            }
            dlg.show()
        }
        return level
    }
}