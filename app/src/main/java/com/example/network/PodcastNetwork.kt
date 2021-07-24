package com.example.network

import com.example.model.PagingData
import com.example.model.genre.PodcastGenre
import com.example.model.random_podcast.RandomPod
import com.example.model.search.SearchedPodcast
import com.example.model.specificPodcast.SpecificPodcast
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

    @GET("podcasts/{id}?")
    suspend fun specificPodcast(@Path("id")id: String):Response<SpecificPodcast>


    @GET("search?q=sta&sort_by_date=0&type=podcast&offset=0&len_min=10&len_max=30&genre_ids=68%2C82&published_before=1580172454000&published_after=0&only_in=title%2Cdescription&language=English&safe_mode=0")
    suspend fun searchPodcast(@Query("q")q: String):Response<SearchedPodcast>


}

