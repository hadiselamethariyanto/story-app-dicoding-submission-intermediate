package com.example.submissionintermediate.domain

import androidx.paging.PagingData
import com.example.submissionintermediate.data.local.entities.StoryEntity
import com.example.submissionintermediate.data.mechanism.Resource
import com.example.submissionintermediate.data.remote.network.ApiResponse
import com.example.submissionintermediate.data.remote.response.FileUploadResponse
import com.example.submissionintermediate.data.remote.response.LoginResponse
import com.example.submissionintermediate.data.remote.response.RegisterResponse
import com.example.submissionintermediate.data.remote.response.StoriesResponse
import com.example.submissionintermediate.model.User
import kotlinx.coroutines.flow.Flow
import java.io.File

interface IDicodingRepository {

    fun register(name: String, email: String, password: String): Flow<Resource<RegisterResponse>>

    fun login(email: String, password: String): Flow<Resource<LoginResponse>>

    fun getAllStories(): Flow<Resource<StoriesResponse>>

    fun getAllStoriesFromLocal(): Flow<List<StoryEntity>>

    fun getPagingStories(): Flow<PagingData<StoryEntity>>

    suspend fun getStories(): Flow<ApiResponse<StoriesResponse>>

    fun getUser(): Flow<User>

    fun addNewStory(file: File, description: String): Flow<Resource<FileUploadResponse>>
}