package com.example.remote_datasource.feed

import com.example.remote_datasource.RemoteRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FeedUseCase(private val repository: RemoteRepository) {
    suspend operator fun invoke() = repository.getFeed()
}