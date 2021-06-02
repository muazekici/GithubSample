package com.muazekici.n11sample.data.remote_source

data class SearchResponseDTO(val items: List<UserResponseDTO>)

data class UserResponseDTO(val id: Long, val login: String, val avatarUrl: String)

data class UserDetailResponseDTO(val id: Long, val login: String, val avatar_url: String?)