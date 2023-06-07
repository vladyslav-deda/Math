package com.example.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.holder.SessionHolder
import com.example.presentation.R
import com.example.presentation.base.extension.getImageRes
import com.example.presentation.base.extension.getSelectedItem

class HomeViewModel : ViewModel() {

    fun getSelectedItemIcon() =
        SessionHolder.currentUser?.shopItems?.getSelectedItem()?.getImageRes()
            ?: R.drawable.logo_cat
}