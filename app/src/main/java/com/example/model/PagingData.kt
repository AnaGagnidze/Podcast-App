package com.example.model

data class PagingData(
    val id : Int,
    val name: String,
    val parent_id: Int,
    val podcasts : List<Podcasts>,
    val total : Int,
    val has_next : Boolean,
    val has_previous : Boolean,
    val page_number: Int,
    val previous_page_number:Int,
    val next_page_number: Int,
    val listennotes_url: String
) {
}