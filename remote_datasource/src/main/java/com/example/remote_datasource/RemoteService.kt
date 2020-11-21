package com.example.remote_datasource

import com.example.remote_datasource.model.Feed
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.http.GET

interface RemoteServiceInterface{
    @GET("feed")
    fun getFeed(): Observable<Feed?>?
}

class RemoteRepository(retrofit: Retrofit) {
    private val client = retrofit.create(RemoteServiceInterface::class.java)

    fun getFeed() = client.getFeed()
}