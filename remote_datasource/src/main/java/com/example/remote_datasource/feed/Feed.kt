package com.example.remote_datasource.feed

data class Feed (
    val feedList: List<FeedItem>
)

data class FeedItem(
    val id: Int,
    val imageUrl: String
)