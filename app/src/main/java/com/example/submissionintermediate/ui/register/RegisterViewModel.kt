package com.example.submissionintermediate.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.submissionintermediate.domain.IDicodingRepository

class RegisterViewModel(private val dicodingRepository: IDicodingRepository) : ViewModel() {

    private val _formState = MutableLiveData<Boolean>()
    val formState: LiveData<Boolean> get() = _formState

    init {
        _formState.value = false
    }

    fun dataChanged(email: String, fullName: String, password: String) {
        _formState.value = email.isNotEmpty() && fullName.isNotEmpty() && password.length >= 6
    }

    fun register(name: String, email: String, password: String) =
        dicodingRepository.register(name, email, password).asLiveData()
}