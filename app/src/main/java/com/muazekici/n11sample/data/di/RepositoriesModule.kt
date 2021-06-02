package com.muazekici.n11sample.data.di

import com.muazekici.n11sample.data.repos_impl.UserDetailRepositoryImpl
import com.muazekici.n11sample.data.repos_impl.UserFavoritesRepositoryImpl
import com.muazekici.n11sample.data.repos_impl.UserSearchRepositoryImpl
import com.muazekici.n11sample.gateways_adapters.repos.UserDetailRepository
import com.muazekici.n11sample.gateways_adapters.repos.UserFavoritesRepository
import com.muazekici.n11sample.gateways_adapters.repos.UserSearchRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoriesModule {

    @Binds
    abstract fun provideUserFavoritesRepository(repo: UserFavoritesRepositoryImpl): UserFavoritesRepository

    @Binds
    abstract fun provideUserSearchRepository(repo: UserSearchRepositoryImpl): UserSearchRepository

    @Binds
    abstract fun provideUserDetailRepository(repo: UserDetailRepositoryImpl): UserDetailRepository

}