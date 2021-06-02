package com.muazekici.n11sample.use_cases

import com.muazekici.n11sample.gateways_adapters.models.UserSimple
import com.muazekici.n11sample.gateways_adapters.repos.UserSearchRepository
import com.muazekici.n11sample.use_cases.base.UseCaseSimple
import javax.inject.Inject

class UserSearchUseCase @Inject constructor(private val userSearchRepo: UserSearchRepository) :
    UseCaseSimple<String, List<UserSimple>>() {

    override suspend fun execution(parameter: String): List<UserSimple> {
        return if (parameter.isEmpty()) emptyList() else userSearchRepo.getUsers(parameter)
    }
}