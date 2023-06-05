package com.example.domain.firebase_users_db

import com.example.domain.holder.model.User
import com.example.domain.shop.model.ShopItem
import com.google.firebase.database.DataSnapshot

interface FirebaseUsersDbRepository {

    suspend fun updateMoneyBalance(
        userName: String,
        newBalance: Int
    )

    suspend fun registerNewUser(
        user: User,
        onSuccess: () -> Unit,
        onError: () -> Unit
    )

    suspend fun checkingIsUserRegistered(
        nickname: String,
        onSuccess: (isUserHasBeenRegistered: Boolean) -> Unit,
        onError: () -> Unit
    )

    suspend fun authUser(
        nickname: String,
        onSuccess: (dataSnapshot: DataSnapshot) -> Unit,
        onError: () -> Unit
    )

    suspend fun updateShopValuesForUser(
        username:String,
        shopValuesList:List<ShopItem>
    )
}