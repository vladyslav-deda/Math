package com.example.domain.holder

import com.example.domain.firebase_users_db.model.User

object SessionHolder {

    var currentUser: User? = null
    var isUserAuthorized: Boolean = false
}