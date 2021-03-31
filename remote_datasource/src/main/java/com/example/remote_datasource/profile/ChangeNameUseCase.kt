package com.example.remote_datasource.profile

import com.example.remote_datasource.RemoteRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ChangeNameUseCase(private val repository: RemoteRepository) {
    suspend operator fun invoke(profile: Profile) = repository.changeName(profile)
}