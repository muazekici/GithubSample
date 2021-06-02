package com.muazekici.n11sample.data.local_source

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserFavoriteEntity::class], version = 1, exportSchema = false)
abstract class AppDB : RoomDatabase() {
    abstract fun userFavoriteDAO(): UserFavoriteDao
}
