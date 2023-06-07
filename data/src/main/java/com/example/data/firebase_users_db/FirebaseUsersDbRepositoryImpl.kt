package com.example.data.firebase_users_db

import com.example.data.firebase_users_db.di.USERS_DB_NAME
import com.example.domain.firebase_users_db.FirebaseUsersDbRepository
import com.example.domain.firebase_users_db.model.User
import com.example.domain.shop.model.ShopItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject
import javax.inject.Named

class FirebaseUsersDbRepositoryImpl @Inject constructor(
    @Named(USERS_DB_NAME) val usersDbReference: DatabaseReference,
) : FirebaseUsersDbRepository {

    override suspend fun updateMoneyBalance(
        userName: String,
        newBalance: Int
    ) {
        usersDbReference.child(userName).child(MONEY_BALANCE).setValue(newBalance)
    }

    override suspend fun registerNewUser(
        user: User,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        user.userName?.let {
            usersDbReference.child(it).setValue(user).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onError()
                }
            }
        }
    }

    override suspend fun checkingIsUserRegistered(
        nickname: String
    ): Boolean {
        var isUserHasBeenRegistered = false
        usersDbReference.child(nickname).get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    isUserHasBeenRegistered = it.result.exists()
                }
            }
        return isUserHasBeenRegistered
    }

    override suspend fun authUser(
        nickname: String,
        onSuccess: (dataSnapshot: DataSnapshot) -> Unit,
        onError: () -> Unit
    ) {
        usersDbReference.child(nickname).get().addOnCompleteListener {
            if (it.isSuccessful) {
                onSuccess(it.result)
            } else {
                onError()
            }
        }
    }

    override suspend fun updateShopValuesForUser(username: String, shopValuesList: List<ShopItem>) {
        usersDbReference.child(username).child(SHOP_ITEMS).setValue(shopValuesList)
    }

    companion object {
        private const val MONEY_BALANCE = "moneyBalance"
        private const val SHOP_ITEMS = "shopItems"
    }
}