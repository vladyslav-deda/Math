package com.example.presentation.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.firebase_users_db.usecase.AuthUserUseCase
import com.example.domain.firebase_users_db.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUserUseCase: AuthUserUseCase
) : ViewModel() {

    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String> = _nickname

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _requestState = MutableLiveData<AuthRequestState>()
    val requestState: LiveData<AuthRequestState> = _requestState

    fun updateEmail(email: String) {
        _nickname.postValue(email)
    }

    fun updatePassword(password: String) {
        _password.postValue(password)
    }

    fun authUser() {
        _nickname.value?.let {
            viewModelScope.launch {
                _requestState.postValue(AuthRequestState.Loading(true))
                authUserUseCase.invoke(
                    nickname = it,
                    onSuccess = { data ->
                        if (data.exists()) {
                            val user = data.getValue(User::class.java) as User
                            if (user.password.equals(_password.value)) {
                                _requestState.postValue(AuthRequestState.Successful(user))
                            } else {
                                _requestState.postValue(AuthRequestState.InvalidPassword)
                            }
                        } else {
                            _requestState.postValue(AuthRequestState.UserWasNotFound)
                        }
                    },
                    onError = {
                        _requestState.postValue(AuthRequestState.AuthError)
                    }
                )
                _requestState.postValue(AuthRequestState.Loading(false))
            }
        }
    }

    fun setIsLoading(isLoading: Boolean) = _isLoading.postValue(isLoading)
}