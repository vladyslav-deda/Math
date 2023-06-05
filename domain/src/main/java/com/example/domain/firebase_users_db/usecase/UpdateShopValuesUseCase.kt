package com.example.domain.firebase_users_db.usecase

import com.example.domain.firebase_users_db.FirebaseUsersDbRepository
import com.example.domain.shop.model.ShopItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateShopValuesUseCase(private val repository: FirebaseUsersDbRepository) {

    suspend operator fun invoke(username: String, shopValuesList: List<ShopItem>) {
        withContext(Dispatchers.IO) {
            repository.updateShopValuesForUser(username, shopValuesList)
        }
    }
}