package com.example.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.model.favorites.FavoritePodcast

@Database(entities = [FavoritePodcast::class], version = 1)
abstract class PodcastsDatabase: RoomDatabase() {
    abstract fun podcastDao(): PodcastDao
}