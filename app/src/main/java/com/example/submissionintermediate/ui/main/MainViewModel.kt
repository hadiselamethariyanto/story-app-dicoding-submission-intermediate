package com.example.submissionintermediate.ui.main

import androidx.lifecycle.*
import com.example.submissionintermediate.data.preferences.UserPreference
import com.example.submissionintermediate.domain.IDicodingRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(
    private val userPreference: UserPreference,
    private val dicodingRepository: IDicodingRepository
) : ViewModel() {

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> get() = _isLogin

    fun getUser() = viewModelScope.launch {
        userPreference.getUser().collectLatest {
            _isLogin.value = it.isLogin
        }
    }

    fun logout() = viewModelScope.launch {
        userPreference.logout()
    }

    fun getPagingStories() = dicodingRepository.getPagingStories().asLiveData()
}