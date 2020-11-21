package com.example.memeapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.remote_datasource.profile.ProfileUseCase
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class ProfileViewModel : ViewModel(), KoinComponent {

    private val profileUseCase: ProfileUseCase by inject()
    private val compositeDisposable = CompositeDisposable()

    private val _text = MutableLiveData<String>().apply {
        value = "This is profile Fragment"
    }
    val text: LiveData<String> = _text

    fun getProfile(){
        profileUseCase()?.subscribe({
            print(it)
        }, {
            print(it)
        })?.run { compositeDisposable.add(this) }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}