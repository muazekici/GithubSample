package com.muazekici.n11sample.use_cases

import com.muazekici.n11sample.gateways_adapters.models.UserSimple
import com.muazekici.n11sample.gateways_adapters.repos.UserFavoritesRepository
import com.muazekici.n11sample.use_cases.base.UseCaseSimple
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavUpdatedUseCase @Inject constructor(private val favRepo: UserFavoritesRepository) :
    UseCaseSimple<Pair<List<UserSimple>, Long>, Int>() {

    override suspend fun execution(parameter: Pair<List<UserSimple>, Long>): Int {
        val favStatus = favRepo.isUserFavById(parameter.second)
        return withContext(Dispatchers.Default) {
            parameter.first.forEachIndexed { index, userSimple ->
                if (userSimple.id == parameter.second) {
                    if (userSimple.isFavorite != favStatus) {
                        userSimple.isFavorite = favStatus
                        return@withContext index
                    } else {
                        return@withContext NOT_CHANGE_IDX
                    }
                }
            }
            return@withContext NOT_CHANGE_IDX
        }
    }
}

const val NOT_CHANGE_IDX = -1