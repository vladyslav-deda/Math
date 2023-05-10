package com.example.presentation.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun updateEmail(email: String) {
        _email.postValue(email)
    }

    fun updatePassword(password: String) {
        _password.postValue(password)
    }

    fun setIsLoading(isLoading: Boolean) = _isLoading.postValue(isLoading)
}