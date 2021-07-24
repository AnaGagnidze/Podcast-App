package com.example.model.search


import com.squareup.moshi.Json
import androidx.annotation.Keep

@Keep
data class Result(
    @Json(name = "description_highlighted")
    val descriptionHighlighted: String,
    @Json(name = "description_original")
    val descriptionOriginal: String,
    @Json(name = "earliest_pub_date_ms")
    val earliestPubDateMs: Long,
    @Json(name = "email")
    val email: String,
    @Json(name = "explicit_content")
    val explicitContent: Boolean,
    @Json(name = "genre_ids")
    val genreIds: List<Int>,
    @Json(name = "id")
    val id: String,
    @Json(name = "image")
    val image: String,
    @Json(name = "itunes_id")
    val itunesId: Int,
    @Json(name = "latest_pub_date_ms")
    val latestPubDateMs: Long,
    @Json(name = "listen_score")
    val listenScore: String,
    @Json(name = "listen_score_global_rank")
    val listenScoreGlobalRank: String,
    @Json(name = "listennotes_url")
    val listennotesUrl: String,
    @Json(name = "publisher_highlighted")
    val publisherHighlighted: String,
    @Json(name = "publisher_original")
    val publisherOriginal: String,
    @Json(name = "rss")
    val rss: String,
    @Json(name = "thumbnail")
    val thumbnail: String,
    @Json(name = "title_highlighted")
    val titleHighlighted: String,
    @Json(name = "title_original")
    val titleOriginal: String,
    @Json(name = "total_episodes")
    val totalEpisodes: Int,
    @Json(name = "website")
    val website: String
)