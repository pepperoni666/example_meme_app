package com.example.memeapp.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel: ViewModel() {
    val nameMutableLiveData = MutableLiveData("")
    val editingMutableLiveData = MutableLiveData(false)
}