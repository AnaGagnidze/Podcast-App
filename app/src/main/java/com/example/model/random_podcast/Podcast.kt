package com.example.model.random_podcast


import com.squareup.moshi.Json
import androidx.annotation.Keep

@Keep
data class Podcast(
    @Json(name = "id")
    val id: String?,
    @Json(name = "image")
    val image: String?,
    @Json(name = "listen_score")
    val listenScore: String?,
    @Json(name = "listen_score_global_rank")
    val listenScoreGlobalRank: String?,
    @Json(name = "listennotes_url")
    val listennotesUrl: String?,
    @Json(name = "publisher")
    val publisher: String?,
    @Json(name = "thumbnail")
    val thumbnail: String?,
    @Json(name = "title")
    val title: String?
)