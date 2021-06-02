package com.muazekici.n11sample.data.repos_impl

import com.muazekici.n11sample.data.local_source.AppDB
import com.muazekici.n11sample.data.local_source.UserFavoriteDao
import com.muazekici.n11sample.data.local_source.UserFavoriteEntity
import com.muazekici.n11sample.data.remote_source.GithubAPI
import com.muazekici.n11sample.data.remote_source.UserResponseDTO
import com.muazekici.n11sample.di.scopes.AppScope
import com.muazekici.n11sample.gateways_adapters.models.UserSimple
import com.muazekici.n11sample.gateways_adapters.repos.UserSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AppScope
class UserSearchRepositoryImpl @Inject constructor(
    private val api: GithubAPI,
    private val appDB: AppDB
) : UserSearchRepository {

    override suspend fun getUsers(query: String): List<UserSimple> {
        return coroutineScope {
            val _users = async { api.getUsers(query) }
            val _favs = async { appDB.userFavoriteDAO().getAll() }

            mergeFavorites(_users.await().items, _favs.await())
        }
    }

    private suspend fun mergeFavorites(
        users: List<UserResponseDTO>,
        favorites: List<UserFavoriteEntity>
    ): List<UserSimple> {
        return withContext(Dispatchers.Default) {
            users.map { u ->
                val f = favorites.find { it.id == u.id }
                UserSimple(u.id, u.login, f != null)
            }
        }
    }
}