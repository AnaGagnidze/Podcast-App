package com.example.network

import com.example.model.PagingData
import com.example.model.genre.PodcastGenre
import com.example.model.random_podcast.RandomPod
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PodcastNetwork {

    @GET("best_podcasts?")
    suspend fun getPopularPodcasts(
        @Query("genre_id")genre_id:Int,
        @Query("page")page:Int,
        @Query("region")region:String,
        @Query("safe_mode")safe_mode:Int):Response<PagingData>

    @GET("just_listen")
    suspend fun justListen():Response<RandomPod>

    @GET("genres?top_level_only=1")
    suspend fun getGenre():Response<PodcastGenre>


}