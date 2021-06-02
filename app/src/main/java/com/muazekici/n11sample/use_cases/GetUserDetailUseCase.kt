package com.muazekici.n11sample.use_cases

import com.muazekici.n11sample.gateways_adapters.models.UserDetail
import com.muazekici.n11sample.gateways_adapters.repos.UserDetailRepository
import com.muazekici.n11sample.use_cases.base.UseCaseSimple
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(private val userDetailRepository: UserDetailRepository) :
    UseCaseSimple<String, UserDetail>() {

    override suspend fun execution(parameter: String): UserDetail {
        return userDetailRepository.getUserDetail(parameter)
    }
}