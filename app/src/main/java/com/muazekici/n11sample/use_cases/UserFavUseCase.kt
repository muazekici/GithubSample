package com.muazekici.n11sample.use_cases

import com.muazekici.n11sample.gateways_adapters.models.UserSimple
import com.muazekici.n11sample.gateways_adapters.repos.UserFavoritesRepository
import com.muazekici.n11sample.gateways_adapters.repos.UserSearchRepository
import com.muazekici.n11sample.use_cases.base.UseCaseSimple
import javax.inject.Inject

class UserFavUseCase @Inject constructor(private val userFavoriteRepo: UserFavoritesRepository) :
    UseCaseSimple<Triple<Long, Boolean, String>, Unit>() {

    open override suspend fun execution(parameter: Triple<Long, Boolean, String>) {
        if (parameter.second) {
            userFavoriteRepo.favoriteUser(parameter.first, parameter.third)
        } else {
            userFavoriteRepo.deleteFavoriteUser(parameter.first)
        }
    }
}