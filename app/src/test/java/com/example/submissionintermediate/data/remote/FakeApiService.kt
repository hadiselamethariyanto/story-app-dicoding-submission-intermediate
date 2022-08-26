package com.example.submissionintermediate.data.remote

import com.example.submissionintermediate.data.remote.network.ApiService
import com.example.submissionintermediate.data.remote.response.FileUploadResponse
import com.example.submissionintermediate.data.remote.response.LoginResponse
import com.example.submissionintermediate.data.remote.response.RegisterResponse
import com.example.submissionintermediate.data.remote.response.StoriesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FakeApiService : ApiService {

    override suspend fun register(name: String, email: String, password: String): RegisterResponse {
        TODO("Not yet implemented")
    }

    override suspend fun login(email: String, password: String): LoginResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getAllStories(
        page: Int,
        size: Int,
        location: Boolean,
        token: String
    ): StoriesResponse {
        TODO("Not yet implemented")
    }

    override suspend fun addNewStory(
        file: MultipartBody.Part,
        description: RequestBody,
        token: String
    ): FileUploadResponse {
        TODO("Not yet implemented")
    }
}