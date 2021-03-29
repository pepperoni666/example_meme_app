package com.example.remote_datasource.feed

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Feed (
    val feedList: List<FeedItem>?
)

@Parcelize
data class FeedItem(
    val id: Int,
    val imageUrl: String?,
    val title: String?,
    val likes: Int?,
    var liked: Boolean = false
): Parcelable