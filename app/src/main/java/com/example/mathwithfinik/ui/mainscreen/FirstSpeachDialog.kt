package com.example.mathwithfinik.ui.mainscreen

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import com.example.mathwithfinik.R

object FirstSpeachDialog {

    fun showDialog(context: Context?) {
        val dialog = context?.let { Dialog(it) }
        dialog?.let { dialog ->
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_first_speach_layout)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.findViewById<Button>(R.id.speach_dialog_ok_button).setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }

}