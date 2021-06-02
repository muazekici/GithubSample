package com.muazekici.n11sample.data.repos_impl

import com.muazekici.n11sample.data.local_source.AppDB
import com.muazekici.n11sample.data.mappers.UserDetailResponseDTO2UserDetail
import com.muazekici.n11sample.data.remote_source.GithubAPI
import com.muazekici.n11sample.gateways_adapters.models.UserDetail
import com.muazekici.n11sample.gateways_adapters.repos.UserDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserDetailRepositoryImpl @Inject constructor(
    private val githubAPI: GithubAPI,
    private val appDB: AppDB
) : UserDetailRepository {

    override suspend fun getUserDetail(userName: String): UserDetail {
        val resp = githubAPI.getUserDetail(userName)
        val favUser = appDB.userFavoriteDAO().getFavById(resp.id)

        return withContext(Dispatchers.Default) {
            UserDetailResponseDTO2UserDetail.map(resp).also { it.isFavorite = favUser != null }
        }
    }
}