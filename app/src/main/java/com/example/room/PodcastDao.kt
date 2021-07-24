package com.example.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.model.favorites.FavoritePodcast

@Dao
interface PodcastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPodcast(favoritePodcast: FavoritePodcast)

    @Query("SELECT * From favorite_podcasts order by id ASC")
    fun getFavPodcasts(): LiveData<List<FavoritePodcast>>

    @Delete
    suspend fun deletePodcast(favoritePodcast: FavoritePodcast)
}