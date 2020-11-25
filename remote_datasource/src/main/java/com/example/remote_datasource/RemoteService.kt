package com.example.remote_datasource

import com.example.remote_datasource.feed.Feed
import com.example.remote_datasource.profile.Profile
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

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

    fun getFeed() = client.getFeed()
    fun memeLiked(updatedFeed: Feed) = client.memeLiked(updatedFeed)
    fun getProfile() = client.getProfile()
    fun changeName(profile: Profile) = client.changeName(profile)
}