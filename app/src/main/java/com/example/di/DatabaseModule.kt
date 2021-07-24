package com.example.di

import android.content.Context
import androidx.room.Room
import com.example.room.FavPodcastRepository
import com.example.room.PodcastDao
import com.example.room.PodcastsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun providePodcastDao(podcastsDatabase: PodcastsDatabase): PodcastDao =
        podcastsDatabase.podcastDao()

    @Provides
    @Singleton
    fun providePodcastsDatabase(@ApplicationContext context: Context): PodcastsDatabase =
        Room.databaseBuilder(context, PodcastsDatabase::class.java, "Favorite_podcasts").build()

    @Provides
    fun provideFavPodcastRepository(podcastDao: PodcastDao): FavPodcastRepository = FavPodcastRepository(podcastDao)

}