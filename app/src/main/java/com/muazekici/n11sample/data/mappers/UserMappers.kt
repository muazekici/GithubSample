package com.muazekici.n11sample.data.mappers

import com.muazekici.n11sample.data.local_source.UserFavoriteEntity
import com.muazekici.n11sample.data.remote_source.UserDetailResponseDTO
import com.muazekici.n11sample.data.remote_source.UserResponseDTO
import com.muazekici.n11sample.gateways_adapters.models.UserDetail
import com.muazekici.n11sample.gateways_adapters.models.UserSimple

object UserResponseDTO2UserSimple : ObjMapper<UserResponseDTO, UserSimple> {

    override fun map(input: UserResponseDTO): UserSimple {
        return UserSimple(input.id, input.login)
    }
}

object UserResponseDTOList2UserSimpleList :
    ListMapperImpl<UserResponseDTO, UserSimple>(UserResponseDTO2UserSimple)



object UserFavoriteEntity2UserSimple : ObjMapper<UserFavoriteEntity, UserSimple> {

    override fun map(input: UserFavoriteEntity): UserSimple {
        return UserSimple(input.id, input.userName, isFavorite = true)
    }
}

object UserFavoriteEntityList2UserSimpleList :
    ListMapperImpl<UserFavoriteEntity, UserSimple>(UserFavoriteEntity2UserSimple)



object UserDetailResponseDTO2UserDetail : ObjMapper<UserDetailResponseDTO, UserDetail> {

    override fun map(input: UserDetailResponseDTO): UserDetail {
        return UserDetail(input.id, input.login, input.avatar_url)
    }
}

object UserDetailResponseDTOList2UserDetailList :
    ListMapperImpl<UserDetailResponseDTO, UserDetail>(UserDetailResponseDTO2UserDetail)