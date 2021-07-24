package com.example.model.genre


import android.os.Parcelable
import com.squareup.moshi.Json
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Genre(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "parent_id")
    val parentId: Int?
):Parcelable