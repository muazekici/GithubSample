package com.muazekici.n11sample.gateways_adapters.repos

import com.muazekici.n11sample.gateways_adapters.models.UserDetail

interface UserDetailRepository {
    suspend fun getUserDetail(userName: String): UserDetail
}