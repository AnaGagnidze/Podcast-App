package com.example.model.search


import com.squareup.moshi.Json
import androidx.annotation.Keep

@Keep
data class SearchedPodcast(
    @Json(name = "count")
    val count: Int,
    @Json(name = "next_offset")
    val nextOffset: Int,
    @Json(name = "results")
    val results: List<Result>,
    @Json(name = "took")
    val took: Double,
    @Json(name = "total")
    val total: Int
)