package com.example.remote_datasource

import com.example.remote_datasource.model.Feed
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UseCase(private val repository: RemoteRepository) {
    operator fun invoke(): Observable<Feed?>? {
        return repository.getFeed()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
    }
}