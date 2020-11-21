package com.example.memeapp.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.remote_datasource.feed.FeedUseCase
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class FeedViewModel : ViewModel(), KoinComponent{

    private val feedUseCase: FeedUseCase by inject()
    private val compositeDisposable = CompositeDisposable()

    private val _text = MutableLiveData<String>().apply {
        value = "This is feed Fragment"
    }
    val text: LiveData<String> = _text

    fun getFeed(){
        feedUseCase()?.subscribe({
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