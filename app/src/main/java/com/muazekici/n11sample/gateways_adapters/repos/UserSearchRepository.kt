package com.muazekici.n11sample.gateways_adapters.repos

import com.muazekici.n11sample.gateways_adapters.models.UserSimple

interface UserSearchRepository {

    suspend fun getUsers(query: String): List<UserSimple>
}