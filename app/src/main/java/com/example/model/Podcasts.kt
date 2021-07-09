package com.example.model

data class Podcasts(
    val id: String,
    val title: String,
    val publisher: String,
    val image : String,
    val thumbnail: String,
    val listennotes_url: String,
    val total_episodes: String,
    val description :String,
    val website:String?,
    val type: String,
    val genre_ids: List<Int>


) {
}