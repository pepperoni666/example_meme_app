package com.example.remote_datasource

import com.example.remote_datasource.feed.Feed
import com.example.remote_datasource.profile.Profile
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.http.GET

interface RemoteServiceInterface{
    @GET("feed")
    fun getFeed(): Observable<Feed?>?

    @GET("Profile")
    fun getProfile(): Observable<Profile?>?
}

class RemoteRepository(retrofit: Retrofit) {
    private val client = retrofit.create(RemoteServiceInterface::class.java)

    fun getFeed() = client.getFeed()
    fun getProfile() = client.getProfile()
}