package com.muazekici.n11sample.gateways_adapters.repos

interface UserFavoritesRepository {

    suspend fun favoriteUser(userId: Long, userName: String)
    suspend fun deleteFavoriteUser(userId: Long)
    suspend fun isUserFavById(userId: Long): Boolean

}