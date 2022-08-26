package com.example.submissionintermediate.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.submissionintermediate.domain.IDicodingRepository

class LoginViewModel(private val dicodingRepository: IDicodingRepository) : ViewModel() {

    private val _formState = MutableLiveData<Boolean>()
    val formState: LiveData<Boolean> get() = _formState

    init {
        _formState.value = false
    }

    fun dataChanged(email: Boolean, password: Boolean) {
        _formState.value = email && password
    }

    fun login(email: String, password: String) =
        dicodingRepository.login(email, password).asLiveData()

}