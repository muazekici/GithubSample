package com.muazekici.n11sample.data.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.muazekici.n11sample.data.firestore_source.FirestoreAPI
import com.muazekici.n11sample.data.firestore_source.FirestoreAPIImpl
import com.muazekici.n11sample.di.qualifiers.ApplicationContext
import com.muazekici.n11sample.di.scopes.AppScope
import com.muazekici.n11sample.data.local_source.AppDB
import dagger.Module
import dagger.Provides


@Module(includes = [RetrofitModule::class])
class DataSourcesModule {

    @Provides
    @AppScope
    fun provideDbInstance(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDB::class.java, DB_NAME).fallbackToDestructiveMigration()
            .build()

    @Provides
    @AppScope
    fun provideFirestoreInstance() = Firebase.firestore

    @Provides
    @AppScope
    fun provideFirestoreAPI(firestoreApi: FirestoreAPIImpl): FirestoreAPI = firestoreApi
}

const val DB_NAME = "N11SampleDB"