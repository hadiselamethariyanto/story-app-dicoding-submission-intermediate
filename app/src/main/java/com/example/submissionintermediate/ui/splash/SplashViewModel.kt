package com.example.submissionintermediate.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.submissionintermediate.data.preferences.UserPreference

class SplashViewModel(private val userPreference: UserPreference) : ViewModel() {

    fun getUser() = userPreference.getUser().asLiveData()

}