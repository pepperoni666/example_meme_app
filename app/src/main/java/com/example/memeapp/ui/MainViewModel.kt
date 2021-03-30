package com.example.memeapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.remote_datasource.feed.Feed
import com.example.remote_datasource.feed.FeedItem
import com.example.remote_datasource.feed.FeedUseCase
import com.example.remote_datasource.feed.MemeLikedUseCase
import com.example.remote_datasource.profile.Profile
import com.example.remote_datasource.profile.ProfileUseCase
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class MainViewModel: ViewModel(), KoinComponent {
    private val profileUseCase: ProfileUseCase by inject()
    private val profileMutableLiveData = MutableLiveData<Profile>()
    val profileLiveData: LiveData<Profile> = profileMutableLiveData
    val feedLoading = MutableLiveData(false)
    val profileLoading = MutableLiveData(false)
    private val compositeDisposable = CompositeDisposable()
    private val feedUseCase: FeedUseCase by inject()
    private val memeLikedUseCase: MemeLikedUseCase by inject()
    private val feedMutableLiveData = MutableLiveData<List<FeedItem>>()
    val feedLiveData: LiveData<List<FeedItem>> = feedMutableLiveData

    private fun handleLoadingFeed(observable: Observable<Feed?>?){
        observable
            ?.doOnSubscribe { feedLoading.postValue(true) }
            ?.doOnTerminate { feedLoading.postValue(false) }
            ?.subscribe({feed ->
                feed?.feedList?.let { list ->
                    feedMutableLiveData.postValue(list.shuffled())
                }
            }, { throwable ->
                throwable.printStackTrace()
            })?.run { compositeDisposable.add(this) }
    }

    fun likeMeme(item: FeedItem){
        val updatedList = feedMutableLiveData.value?.toMutableList()
        updatedList?.set(updatedList.indexOf(item), item.copy( liked = !item.liked ))
        updatedList?.let {
            handleLoadingFeed(memeLikedUseCase(Feed(it)))

        }
    }

    fun updateProfile(profile: Profile){
        profileMutableLiveData.postValue(profile)
    }

    fun getFeed(){
        handleLoadingFeed(feedUseCase())
    }

    fun getProfile(){
        profileUseCase()
            ?.doOnSubscribe { profileLoading.postValue(true) }
            ?.doOnTerminate { profileLoading.postValue(false) }
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