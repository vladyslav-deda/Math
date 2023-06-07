package com.example.domain.firebase_users_db

import com.example.domain.firebase_users_db.model.User
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
        nickname: String
    ) : Boolean

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