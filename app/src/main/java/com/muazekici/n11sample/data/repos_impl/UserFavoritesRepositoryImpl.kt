package com.muazekici.n11sample.data.repos_impl

import com.muazekici.n11sample.data.firestore_source.FirestoreAPI
import com.muazekici.n11sample.data.local_source.AppDB
import com.muazekici.n11sample.data.local_source.UserFavoriteDao
import com.muazekici.n11sample.data.local_source.UserFavoriteEntity
import com.muazekici.n11sample.di.scopes.AppScope
import com.muazekici.n11sample.gateways_adapters.repos.UserFavoritesRepository
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

@AppScope
class UserFavoritesRepositoryImpl @Inject constructor(
    private val appDB: AppDB,
    private val firestoreAPI: FirestoreAPI
) : UserFavoritesRepository {

    override suspend fun favoriteUser(userId: Long, userName: String) {
        appDB.userFavoriteDAO().insert(UserFavoriteEntity(userId, userName))
        firestoreAPI.saveFavUser(userId, userName)
    }

    override suspend fun deleteFavoriteUser(userId: Long) {
        appDB.userFavoriteDAO().delete(UserFavoriteEntity(userId, ""))
        firestoreAPI.deleteFavUser(userId)
    }

    override suspend fun isUserFavById(userId: Long): Boolean {
        return appDB.userFavoriteDAO().getFavById(userId) != null
    }

}