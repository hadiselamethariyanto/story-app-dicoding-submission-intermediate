package com.example.submissionintermediate.ui.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.submissionintermediate.domain.IDicodingRepository

class MapsViewModel(private val dicodingRepository: IDicodingRepository) : ViewModel() {

    fun getAllStoriesFromLocal() = dicodingRepository.getAllStoriesFromLocal().asLiveData()
}