package com.example.submissionintermediate.data.remote

import com.example.submissionintermediate.data.remote.network.ApiResponse
import com.example.submissionintermediate.data.remote.network.ApiService
import com.example.submissionintermediate.data.remote.response.FileUploadResponse
import com.example.submissionintermediate.data.remote.response.LoginResponse
import com.example.submissionintermediate.data.remote.response.RegisterResponse
import com.example.submissionintermediate.data.remote.response.StoriesResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun register(
        name: String,
        email: String,
        password: String
    ): Flow<ApiResponse<RegisterResponse>> {
        return flow {
            try {
                val response = apiService.register(name, email, password)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                val error = (e as? HttpException)?.response()?.errorBody()?.string()
                if (error != null) {
                    val response = Gson().fromJson(error, RegisterResponse::class.java)
                    emit(ApiResponse.Error(response.message ?: "error from server"))
                } else {
                    emit(ApiResponse.Error(e.message.toString()))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun login(email: String, password: String): Flow<ApiResponse<LoginResponse>> {
        return flow {
            try {
                val response = apiService.login(email, password)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                val error = (e as? HttpException)?.response()?.errorBody()?.string()
                if (error != null) {
                    val response = Gson().fromJson(error, RegisterResponse::class.java)
                    emit(ApiResponse.Error(response.message ?: "error from server"))
                } else {
                    emit(ApiResponse.Error(e.message.toString()))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAllStories(
        token: String,
        page: Int,
        size: Int
    ): Flow<ApiResponse<StoriesResponse>> {
        return flow {
            try {
                val response = apiService.getAllStories(page, size, true, token)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                val error = (e as? HttpException)?.response()?.errorBody()?.string()
                if (error != null) {
                    val response = Gson().fromJson(error, RegisterResponse::class.java)
                    emit(ApiResponse.Error(response.message ?: "error from server"))
                } else {
                    emit(ApiResponse.Error(e.message.toString()))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAllStoriesDirectly(
        token: String,
        page: Int,
        size: Int
    ): StoriesResponse = apiService.getAllStories(page, size, true, token)

    suspend fun addNewStory(
        file: File,
        description: String,
        lat: Float?,
        lon: Float?,
        token: String
    ): Flow<ApiResponse<FileUploadResponse>> {
        return flow {
            try {
                val descriptionRequestBody = description.toRequestBody("text/plain".toMediaType())
                var latRequestBody: RequestBody? = null
                var lonRequestBody: RequestBody? = null

                if (lat != null && lon != null) {
                    latRequestBody = lat.toString().toRequestBody("text/plain".toMediaType())
                    lonRequestBody = lon.toString().toRequestBody("text/plain".toMediaType())
                }
                val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "photo",
                    file.name,
                    requestImageFile
                )
                val response = apiService.addNewStory(
                    imageMultipart,
                    descriptionRequestBody,
                    latRequestBody,
                    lonRequestBody,
                    token
                )
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                val error = (e as? HttpException)?.response()?.errorBody()?.string()
                if (error != null) {
                    val response = Gson().fromJson(error, RegisterResponse::class.java)
                    emit(ApiResponse.Error(response.message ?: "error from server"))
                } else {
                    emit(ApiResponse.Error(e.message.toString()))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

}