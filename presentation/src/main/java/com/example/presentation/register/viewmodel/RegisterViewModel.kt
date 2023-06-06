package com.example.presentation.register.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.firebase_users_db.usecase.CheckingIsUserRegisteredUseCase
import com.example.domain.firebase_users_db.usecase.RegisterNewUserUseCase
import com.example.domain.holder.SessionHolder
import com.example.domain.holder.model.User
import com.example.domain.shop.model.ShopItem
import com.example.presentation.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerNewUserUseCase: RegisterNewUserUseCase,
    private val isUserRegisteredUseCase: CheckingIsUserRegisteredUseCase
) : ViewModel() {

    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String> = _nickname

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _rotationDegrees = MutableLiveData(0.0f)
    val rotationDegrees: LiveData<Float> = _rotationDegrees

    private val _isAdmin = MutableLiveData<Boolean>()
    val isAdmin: LiveData<Boolean> = _isAdmin

    private val _isRegistrationSuccessful = MutableLiveData<Boolean>()
    val isRegistrationSuccessful: LiveData<Boolean> = _isRegistrationSuccessful

    fun updateEmail(email: String) {
        _nickname.postValue(email)
    }

    fun updatePassword(password: String) {
        _password.postValue(password)
    }

    fun increaseDegreesOfRotation() {
        val newValue = _rotationDegrees.value?.plus(ROTATE_DEGREES)
        _rotationDegrees.postValue(newValue)
    }

    fun switchToAdminRegistrationMode() = _isAdmin.postValue(true)

    fun registerNewUser() {
        val level = if (_isAdmin.value == true) {
            Constants.ADMIN_ACCESS_LEVEL
        } else {
            Constants.PUPIL_ACCESS_LEVEL
        }
        val newUser = User(
            userName = _nickname.value,
            password = _password.value,
            moneyBalance = 0,
            level = level,
            shopItems = getInitialShopItemsList()
        )
        viewModelScope.launch {
            registerNewUserUseCase.invoke(
                newUser,
                onSuccess = {
                    SessionHolder.apply {
                        currentUser = newUser
                        isUserAuthorized = true
                    }
                    _isRegistrationSuccessful.postValue(true)
                },
                onError = {
                    _isRegistrationSuccessful.postValue(false)
                }
            )
        }
    }

    fun checkingIsUserRegistered(): Boolean {
        var isUserRegistered = false
        _nickname.value?.let {
            viewModelScope.launch {
                isUserRegisteredUseCase.invoke(it,
                    onSuccess = {
                        isUserRegistered = it
                    },
                    onError = {

                    })
            }
        }
        return isUserRegistered
    }

    private fun getInitialShopItemsList(): List<ShopItem> {
        val list = Constants.shopImagesList.map {
            ShopItem(
                id = it,
                price = it * 20
            )
        }
        list[0].apply {
            isSelected = true
            isBought = true
        }
        return list
    }

    companion object {
        private const val ROTATE_DEGREES = 36f
    }
}