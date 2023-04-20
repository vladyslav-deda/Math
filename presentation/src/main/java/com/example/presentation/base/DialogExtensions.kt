package com.example.presentation.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import com.example.presentation.Constants.EASY_LEVEL
import com.example.presentation.Constants.HARD_LEVEL
import com.example.presentation.Constants.MEDIUM_LEVEL
import com.example.presentation.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

object DialogExtensions {

    fun Context.showInfoDialog(
        text: String,
        @DrawableRes imageRes: Int,
        okButtonAction: () -> Unit
    ) {
        Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(R.layout.info_dialog_layout)
            findViewById<AppCompatImageView>(R.id.image_logo).setImageResource(imageRes)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            findViewById<MaterialTextView>(R.id.description).text = text
            findViewById<MaterialButton>(R.id.ok_button).setOnClickListener {
                dismiss()
                okButtonAction()
            }
        }.show()
    }

    fun Context.showLevelSelectionDialog(
        text: String,
        @DrawableRes imageRes: Int,
        levelButtonAction: (level: String) -> Unit
    ) {
        Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(R.layout.level_dialog_layout)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            findViewById<AppCompatImageView>(R.id.image_logo).setImageResource(imageRes)
            findViewById<MaterialTextView>(R.id.description).text = text
            findViewById<Button>(R.id.button_hard).setOnClickListener {
                dismiss()
                levelButtonAction(HARD_LEVEL)
            }
            findViewById<Button>(R.id.button_medium).setOnClickListener {
                dismiss()
                levelButtonAction(MEDIUM_LEVEL)
            }
            findViewById<Button>(R.id.button_easy).setOnClickListener {
                dismiss()
                levelButtonAction(EASY_LEVEL)
            }
        }.show()
    }
}