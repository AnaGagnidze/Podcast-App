package com.example.room

import androidx.lifecycle.LiveData
import com.example.model.favorites.FavoritePodcast
import javax.inject.Inject

class FavPodcastRepository @Inject constructor(private val podcastDao: PodcastDao) {

    /*val getFavPodcasts: LiveData<List<FavoritePodcast>> = podcastDao.getFavPodcasts()*/

    suspend fun addPodcast(favoritePodcast: FavoritePodcast){
        podcastDao.addPodcast(favoritePodcast)
    }

    suspend fun deletePodcast(favoritePodcast: FavoritePodcast){
        podcastDao.deletePodcast(favoritePodcast)
    }

}