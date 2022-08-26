package com.example.submissionintermediate.ui.upload_story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.submissionintermediate.domain.IDicodingRepository
import java.io.File

class UploadStoryViewModel(private val dicodingRepository: IDicodingRepository) : ViewModel() {

    private val _file = MutableLiveData<File?>()

    private val _formState = MutableLiveData<Boolean>()
    val formState: LiveData<Boolean> get() = _formState

    init {
        _formState.value = false
    }

    fun dataChanged(file: File?, description: String) {
        if (file != null) {
            _file.value = file
        }
        _formState.value = _file.value != null && description.isNotEmpty()
    }

    fun addNewStory(description: String) =
        _file.value?.let { dicodingRepository.addNewStory(it, description).asLiveData() }

}