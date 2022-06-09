package com.example.mathwithfinik.ui.mainscreen

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.example.mathwithfinik.R
import com.example.mathwithfinik.room_db.ShopRepository

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
//            dialog.findViewById<AppCompatImageView>(R.id.first_speach_image_finik).setImageResource(
//                ShopRepository(context).getSelected().icon
//            )
            dialog.show()
        }
    }

}