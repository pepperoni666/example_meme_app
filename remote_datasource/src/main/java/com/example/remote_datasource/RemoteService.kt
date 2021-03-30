package com.example.remote_datasource

import com.example.remote_datasource.feed.Feed
import com.example.remote_datasource.feed.FeedItem
import com.example.remote_datasource.profile.Profile
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import java.util.concurrent.TimeUnit

interface RemoteServiceInterface{
    @GET("feed")
    fun getFeed(): Observable<Feed?>?

    @GET("Profile")
    fun getProfile(): Observable<Profile?>?

    @PUT("Profile")
    fun changeName(@Body profile: Profile): Observable<Profile?>?

    @PUT("feed")
    fun memeLiked(@Body updatedFeed: Feed): Observable<Feed?>?
}

class RemoteRepository(retrofit: Retrofit) {
    private val client = retrofit.create(RemoteServiceInterface::class.java)

    private var mockedProfile = Profile("Aleks", "https://i.picsum.photos/id/1024/1920/1280.jpg?hmac=-PIpG7j_fRwN8Qtfnsc3M8-kC3yb0XYOBfVzlPSuVII")
    private var mockedFeed = Feed(listOf(
        FeedItem(1, "https://i.picsum.photos/id/0/5616/3744.jpg?hmac=3GAAioiQziMGEtLbfrdbcoenXoWAW-zlyEAMkfEdBzQ", "Mock 1", 0),
        FeedItem(2, "https://i.picsum.photos/id/100/2500/1656.jpg?hmac=gWyN-7ZB32rkAjMhKXQgdHOIBRHyTSgzuOK6U0vXb1w", "Mock 2", 0),
        FeedItem(3, "https://i.picsum.photos/id/1002/4312/2868.jpg?hmac=5LlLE-NY9oMnmIQp7ms6IfdvSUQOzP_O3DPMWmyNxwo", "Mock 3", 0)
    ))

    fun getFeed(): Observable<Feed?>? {
        return if(BuildConfig.BUILD_TYPE == "mock") {
            Observable.fromCallable {
                mockedFeed
            }.delay(2000, TimeUnit.MILLISECONDS)
        } else {
            client.getFeed()
        }
    }

    fun memeLiked(updatedFeed: Feed): Observable<Feed?>? {
        return if(BuildConfig.BUILD_TYPE == "mock") {
            Observable.fromCallable {
                mockedFeed = updatedFeed
                mockedFeed
            }.delay(2000, TimeUnit.MILLISECONDS)
        } else {
            client.memeLiked(updatedFeed)
        }
    }

    fun getProfile(): Observable<Profile?>? {
        return if(BuildConfig.BUILD_TYPE == "mock") {
            Observable.fromCallable {
                mockedProfile
            }.delay(2000, TimeUnit.MILLISECONDS)
        } else {
            client.getProfile()
        }
    }

    fun changeName(profile: Profile): Observable<Profile?>? {
        return if(BuildConfig.BUILD_TYPE == "mock") {
            Observable.fromCallable {
                mockedProfile = profile
                mockedProfile
            }.delay(2000, TimeUnit.MILLISECONDS)
        } else {
            client.changeName(profile)
        }

    }
}