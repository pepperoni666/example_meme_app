package com.example.usecase.feed

import com.example.remote_datasource.RemoteRepository
import com.example.remote_datasource.feed.Feed
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FeedUseCase(private val repository: RemoteRepository) {
    operator fun invoke(): Observable<Feed?>? {
        return repository.getFeed()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
    }
}