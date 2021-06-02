package com.muazekici.n11sample.data.firestore_source

interface FirestoreAPI {

    suspend fun saveFavUser(userId: Long, userName: String)
    suspend fun deleteFavUser(userId: Long)

}