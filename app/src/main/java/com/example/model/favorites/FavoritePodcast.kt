package com.example.model.favorites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorite_podcasts")
data class FavoritePodcast(
    @PrimaryKey
    val podcastId: Int,
    val email: String,
    val img: String,
    val title: String,
    val description: String
)
