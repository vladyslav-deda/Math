package com.example.presentation.auth.viewmodel

import com.example.domain.holder.model.User

sealed class AuthRequestState(val user: User? = null, val isLoading: Boolean = false) {

    class Successful(user: User? = null) : AuthRequestState(user = user)
    object InvalidPassword : AuthRequestState()
    object UserWasNotFound : AuthRequestState()
    object AuthError : AuthRequestState()
    class Loading(isLoading: Boolean) : AuthRequestState(isLoading = isLoading)
}