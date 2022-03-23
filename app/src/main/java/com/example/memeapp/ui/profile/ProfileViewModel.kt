package com.example.memeapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.remote_datasource.profile.Profile
import com.example.usecase.profile.ChangeNameUseCase
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class ProfileViewModel: ViewModel(), KoinComponent {
    val loading = MutableLiveData(false)
    val editingMutableLiveData = MutableLiveData(false)

    var profile: Profile? = null

    private val compositeDisposable = CompositeDisposable()
    private val changeNameUseCase: ChangeNameUseCase by inject()
    private val updatedProfileMutableLiveData = MutableLiveData<Profile>()
    val updatedProfileLiveData: LiveData<Profile> = updatedProfileMutableLiveData

    fun updateName(name: String){
        val changedProfile = profile?.copy(name = name) ?: return
        changeNameUseCase(changedProfile)
            ?.doOnSubscribe { loading.postValue(true) }
            ?.doOnTerminate { loading.postValue(false) }
            ?.subscribe({
                updatedProfileMutableLiveData.postValue(it)
            }, {
                it.printStackTrace()
            })?.run { compositeDisposable.add(this) }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}