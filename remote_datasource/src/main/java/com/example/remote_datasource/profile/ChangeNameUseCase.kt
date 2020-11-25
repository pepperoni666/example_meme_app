package com.example.remote_datasource.profile

import com.example.remote_datasource.RemoteRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ChangeNameUseCase(private val repository: RemoteRepository) {
    operator fun invoke(profile: Profile): Observable<Profile?>? {
        return repository.changeName(profile)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
    }
}