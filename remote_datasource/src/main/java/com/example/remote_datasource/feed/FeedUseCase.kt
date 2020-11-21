package com.example.remote_datasource.feed

import com.example.remote_datasource.RemoteRepository
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