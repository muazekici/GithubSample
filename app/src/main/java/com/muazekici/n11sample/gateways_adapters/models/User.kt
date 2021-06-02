package com.muazekici.n11sample.gateways_adapters.models

data class UserSimple(val id: Long, val userName: String, var isFavorite: Boolean = false)

data class UserDetail(
    val id: Long,
    val userName: String,
    val avatarUrl: String?,
    var isFavorite: Boolean = false
)