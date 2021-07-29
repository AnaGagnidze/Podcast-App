package com.example.repo

import com.example.model.favorites.FavoritePodcast
import com.example.room.PodcastDao
import javax.inject.Inject

class FavPodcastRepository @Inject constructor(private val podcastDao: PodcastDao) {

    /*val getFavPodcasts: LiveData<List<FavoritePodcast>> = podcastDao.getFavPodcasts()*/

   suspend fun getAllPodcasts(email: String):List<FavoritePodcast>{
         return podcastDao.getFavPodcasts(email)
    }


    suspend fun addPodcast(favoritePodcast: FavoritePodcast){
        podcastDao.addPodcast(favoritePodcast)
    }

    suspend fun deletePodcast(favoritePodcast: FavoritePodcast){
        podcastDao.deletePodcast(favoritePodcast)
    }



}