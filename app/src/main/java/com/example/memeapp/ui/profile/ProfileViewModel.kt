package com.example.memeapp.ui.profile

import androidx.lifecycle.MutableLiveData
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.Uninitialized
import com.example.remote_datasource.profile.ChangeNameUseCase
import com.example.remote_datasource.profile.Profile
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.lang.Exception

data class ProfileState(val updateProfile: Async<Profile> = Uninitialized) : MavericksState

@KoinApiExtension
class ProfileViewModel(initialState: ProfileState) : MavericksViewModel<ProfileState>(initialState),
    KoinComponent {
    val editingMutableLiveData = MutableLiveData(false)

    var profile: Profile? = null

    private val changeNameUseCase: ChangeNameUseCase by inject()

    fun updateName(name: String) {
        val changedProfile = profile?.copy(name = name) ?: return
        suspend {
            changeNameUseCase(changedProfile) ?: throw Exception()
        }.execute { copy(updateProfile = it) }
    }
}