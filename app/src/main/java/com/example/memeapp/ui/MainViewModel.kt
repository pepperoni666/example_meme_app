package com.example.memeapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.airbnb.mvrx.*
import com.example.remote_datasource.feed.Feed
import com.example.remote_datasource.feed.FeedItem
import com.example.remote_datasource.feed.FeedUseCase
import com.example.remote_datasource.feed.MemeLikedUseCase
import com.example.remote_datasource.profile.Profile
import com.example.remote_datasource.profile.ProfileUseCase
import kotlinx.coroutines.delay
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.lang.Exception

data class MainState(
    val feed: Async<Feed> = Uninitialized,
    val profile: Async<Profile> = Uninitialized
) : MavericksState {
    val feedList get() = feed()?.feedList
}

@KoinApiExtension
class MainViewModel(initialState: MainState) : MavericksViewModel<MainState>(initialState),
    KoinComponent {

    private val profileUseCase: ProfileUseCase by inject()
    private val feedUseCase: FeedUseCase by inject()
    private val memeLikedUseCase: MemeLikedUseCase by inject()

    fun likeMeme(item: FeedItem) = withState { state ->
        val updatedList = state.feedList?.toMutableList()
        updatedList?.set(updatedList.indexOf(item), item.copy(liked = !item.liked))
        updatedList?.let { list ->
            suspend {
                memeLikedUseCase(Feed(list)) ?: throw Exception()
            }.execute { copy(feed = it) }
        }
    }

    fun updateProfile(profile: Profile) = setState {
        copy(profile = Success(profile))
    }

    fun getFeed() {
        suspend {
            feedUseCase()?.let {
                Feed(it.feedList?.shuffled())
            } ?: throw Exception()
        }.execute { copy(feed = it) }
    }

    fun getProfile() {
        suspend {
            profileUseCase() ?: throw Exception()
        }.execute { copy(profile = it) }
    }
}