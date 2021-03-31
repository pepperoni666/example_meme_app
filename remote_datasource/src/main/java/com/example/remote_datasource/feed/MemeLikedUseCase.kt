package com.example.remote_datasource.feed

import com.example.remote_datasource.RemoteRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MemeLikedUseCase(private val repository: RemoteRepository) {
    suspend operator fun invoke(updatedFeed: Feed) = repository.memeLiked(updatedFeed)
}