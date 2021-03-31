package com.example.remote_datasource

import com.example.remote_datasource.feed.Feed
import com.example.remote_datasource.feed.FeedItem
import com.example.remote_datasource.profile.Profile
import io.reactivex.Observable
import kotlinx.coroutines.delay
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import java.util.concurrent.TimeUnit

interface RemoteServiceInterface{
    @GET("feed")
    suspend fun getFeed(): Feed?

    @GET("Profile")
    suspend fun getProfile(): Profile?

    @PUT("Profile")
    suspend fun changeName(@Body profile: Profile): Profile?

    @PUT("feed")
    suspend fun memeLiked(@Body updatedFeed: Feed): Feed?
}

class RemoteRepository(retrofit: Retrofit) {
    private val client = retrofit.create(RemoteServiceInterface::class.java)

    private var mockedProfile = Profile("Aleks", "https://i.picsum.photos/id/1024/1920/1280.jpg?hmac=-PIpG7j_fRwN8Qtfnsc3M8-kC3yb0XYOBfVzlPSuVII")
    private var mockedFeed = Feed(listOf(
        FeedItem(1, "https://i.picsum.photos/id/0/5616/3744.jpg?hmac=3GAAioiQziMGEtLbfrdbcoenXoWAW-zlyEAMkfEdBzQ", "Mock 1", 0),
        FeedItem(2, "https://i.picsum.photos/id/100/2500/1656.jpg?hmac=gWyN-7ZB32rkAjMhKXQgdHOIBRHyTSgzuOK6U0vXb1w", "Mock 2", 0),
        FeedItem(3, "https://i.picsum.photos/id/1002/4312/2868.jpg?hmac=5LlLE-NY9oMnmIQp7ms6IfdvSUQOzP_O3DPMWmyNxwo", "Mock 3", 0)
    ))

    suspend fun getFeed(): Feed? {
        return if(BuildConfig.BUILD_TYPE == "mock") {
            delay(2000)
            mockedFeed
        } else {
            client.getFeed()
        }
    }

    suspend fun memeLiked(updatedFeed: Feed): Feed? {
        return if(BuildConfig.BUILD_TYPE == "mock") {
            delay(2000)
            mockedFeed = updatedFeed
            mockedFeed
        } else {
            client.memeLiked(updatedFeed)
        }
    }

    suspend fun getProfile(): Profile? {
        return if(BuildConfig.BUILD_TYPE == "mock") {
            delay(2000)
            mockedProfile
        } else {
            client.getProfile()
        }
    }

    suspend fun changeName(profile: Profile): Profile? {
        return if(BuildConfig.BUILD_TYPE == "mock") {
            delay(2000)
            mockedProfile = profile
            mockedProfile
        } else {
            client.changeName(profile)
        }

    }
}