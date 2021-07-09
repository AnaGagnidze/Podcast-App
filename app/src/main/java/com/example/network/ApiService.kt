package com.example.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiService {


    val podcastService by lazy { createPodcastService() }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
     fun createPodcastService():PodcastNetwork{
        val retrofitBuilder = Retrofit.Builder()
        retrofitBuilder.baseUrl("https://listen-api.listennotes.com/api/v2/")
        retrofitBuilder.client(
            OkHttpClient().newBuilder()
                .addInterceptor(KeyInterceptor())
                .addInterceptor(loggingInterceptor)
                .build()
        )
        retrofitBuilder.addConverterFactory(mochiConvertor())
        return retrofitBuilder.build().create(PodcastNetwork::class.java)
    }


    private fun mochiConvertor()=
        MoshiConverterFactory.create(
            Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
        )
}