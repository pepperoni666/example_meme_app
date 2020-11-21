package com.example.remote_datasource.profile

import com.example.remote_datasource.RemoteRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProfileUseCase(private val repository: RemoteRepository) {
    operator fun invoke(): Observable<Profile?>? {
        return repository.getProfile()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
    }
}