package com.muazekici.n11sample.data.local_source

import androidx.room.*

@Entity(tableName = "user_favorites")
data class UserFavoriteEntity(@PrimaryKey val id: Long, val userName: String)

@Dao
interface UserFavoriteDao {

    @Query("SELECT * FROM user_favorites")
    suspend fun getAll(): List<UserFavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userFavoriteEntity: UserFavoriteEntity)

    @Delete
    suspend fun delete(userFavoriteEntity: UserFavoriteEntity)

    @Query("SELECT * FROM user_favorites WHERE id = :id")
    suspend fun getFavById(id: Long): UserFavoriteEntity?
}