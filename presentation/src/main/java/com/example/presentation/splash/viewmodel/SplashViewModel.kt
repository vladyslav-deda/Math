package com.example.presentation.splash.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.shop.ShopRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: ShopRepository
) : ViewModel() {

}