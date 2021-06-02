package com.muazekici.n11sample.data.firestore_source

import com.google.firebase.firestore.FirebaseFirestore
import com.muazekici.n11sample.di.scopes.AppScope
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@AppScope
class FirestoreAPIImpl @Inject constructor(private val firestore: FirebaseFirestore) :
    FirestoreAPI {


    override suspend fun saveFavUser(userId: Long, userName: String) {
        firestore.collection(COLLECTION_FAV_USERS).document(userId.toString()).set(
            hashMapOf(
                USER_NAME_KEY to userName
            )
        ).await()
    }

    override suspend fun deleteFavUser(userId: Long) {
        firestore.collection(COLLECTION_FAV_USERS).document(userId.toString()).delete().await()
    }

    companion object {
        private const val COLLECTION_FAV_USERS = "FavUsers"

        private const val USER_NAME_KEY = "UserName"
    }
}