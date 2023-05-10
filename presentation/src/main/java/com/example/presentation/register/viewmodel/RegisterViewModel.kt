package com.example.presentation.register.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _rotationDegrees = MutableLiveData(0.0f)
    val rotationDegrees: LiveData<Float> = _rotationDegrees

    private val _isAdmin = MutableLiveData<Boolean>()
    val isAdmin: LiveData<Boolean> = _isAdmin

    fun updateEmail(email: String) {
        _email.postValue(email)
    }

    fun updatePassword(password: String) {
        _password.postValue(password)
    }

    fun increaseDegreesOfRotation() {
        val newValue = _rotationDegrees.value?.plus(ROTATE_DEGREES)
        _rotationDegrees.postValue(newValue)
    }

    fun switchToAdminRegistrationMode() = _isAdmin.postValue(true)

    companion object {
        private const val ROTATE_DEGREES = 36f
    }
}