package com.muazekici.n11sample.data.di

import com.muazekici.n11sample.di.BASE_URL_TAG
import com.muazekici.n11sample.di.scopes.AppScope
import com.muazekici.n11sample.data.remote_source.GithubAPI
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Named

@Module
class RetrofitModule {


    @Provides
    @AppScope
    fun provideHttpLoggingInterceptor() =
        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.d(message)
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @AppScope
    fun provideHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ) =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @AppScope
    fun provideGsonConverterFactory(): Converter.Factory = GsonConverterFactory.create()

    @Provides
    @AppScope
    fun provideRetrofitInstance(
        @Named(BASE_URL_TAG) baseUrl: String,
        converterFactory: Converter.Factory,
        okHttpClient: OkHttpClient
    ) =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()

    @Provides
    @AppScope
    fun provideMockAPI(retrofit: Retrofit) =
        retrofit.create(GithubAPI::class.java)
}

