package com.example.presentation.base

import com.google.firebase.database.DataSnapshot

sealed class RequestState(val data: DataSnapshot? = null, val isLoading: Boolean = false) {

    class Successful(data: DataSnapshot? = null) : RequestState(data = data)
    class Loading(isLoading: Boolean) : RequestState(isLoading = isLoading)
    object Error : RequestState()
}