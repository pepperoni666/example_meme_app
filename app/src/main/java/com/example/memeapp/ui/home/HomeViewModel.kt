package com.example.memeapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.remote_datasource.UseCase
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class HomeViewModel : ViewModel(), KoinComponent {

    private val useCase: UseCase by inject()
    private val compositeDisposable = CompositeDisposable()

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun getFeed(){
        useCase()?.subscribe({
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