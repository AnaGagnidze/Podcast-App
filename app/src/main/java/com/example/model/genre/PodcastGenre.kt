package com.example.model.genre


import com.squareup.moshi.Json
import androidx.annotation.Keep

@Keep
data class PodcastGenre(
    @Json(name = "genres")
    val genres: List<Genre>
)