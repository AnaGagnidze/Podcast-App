package com.example.model.genre


import com.squareup.moshi.Json
import androidx.annotation.Keep

@Keep
data class Genre(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "parent_id")
    val parentId: Int
)