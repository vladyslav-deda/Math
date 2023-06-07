package com.example.presentation.base.extension

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.androidadvance.topsnackbar.TSnackbar
import com.example.presentation.R

fun View.showSnackBar(message: String) {
    val snackBar = TSnackbar.make(
        this, message,
        TSnackbar.LENGTH_SHORT
    )
    snackBar.setIconRight(R.drawable.close_icon, 16f)
    val snackBarView = snackBar.view
    val param = (snackBarView.layoutParams as ViewGroup.MarginLayoutParams).apply {
        setMargins(30,30,30,30)
    }
    val typeface = ResourcesCompat.getFont(context, R.font.comic_relief)
    snackBarView.apply {
        layoutParams = param
        background = ContextCompat.getDrawable(this.context, R.drawable.back_for_item)
        (findViewById<View>(com.androidadvance.topsnackbar.R.id.snackbar_text) as TextView).apply {
            setOnClickListener { snackBar.dismiss() }
            setTypeface(typeface)
        }
    }
    snackBar.show()
}