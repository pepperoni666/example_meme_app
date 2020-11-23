package com.example.memeapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.remote_datasource.feed.FeedItem
import com.example.remote_datasource.feed.FeedUseCase
import com.example.remote_datasource.profile.Profile
import com.example.remote_datasource.profile.ProfileUseCase
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class MainViewModel: ViewModel(), KoinComponent {
    private val profileUseCase: ProfileUseCase by inject()
    private val profileMutableLiveData = MutableLiveData<Profile>()
    val profileLiveData: LiveData<Profile> = profileMutableLiveData
    val loading = MutableLiveData(false)
    private val compositeDisposable = CompositeDisposable()
    private val feedUseCase: FeedUseCase by inject()
    private val feedMutableLiveData = MutableLiveData<List<FeedItem>>()
    val feedLiveData: LiveData<List<FeedItem>> = feedMutableLiveData

    fun likeMeme(item: FeedItem){
        val updatedList = feedMutableLiveData.value?.toMutableList()
        updatedList?.set(updatedList.indexOf(item), item.copy( liked = !item.liked ))
        feedMutableLiveData.postValue(updatedList)
    }

    fun getFeed(){
        feedUseCase()
            ?.doOnSubscribe { loading.postValue(true) }
            ?.doOnTerminate { loading.postValue(false) }
            ?.subscribe({feed ->
                feed?.feedList?.let {
                    feedMutableLiveData.postValue(it.shuffled())
                }
            }, {
                it.printStackTrace()
            })?.run { compositeDisposable.add(this) }
    }

    fun getProfile(){
        profileUseCase()
            ?.doOnSubscribe { loading.postValue(true) }
            ?.doOnTerminate { loading.postValue(false) }
            ?.subscribe({
                profileMutableLiveData.postValue(it)
            }, {
                it.printStackTrace()
            })?.run { compositeDisposable.add(this) }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}