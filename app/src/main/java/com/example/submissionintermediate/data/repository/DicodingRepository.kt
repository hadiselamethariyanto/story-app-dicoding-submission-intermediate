package com.example.submissionintermediate.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.submissionintermediate.data.local.LocalDataSource
import com.example.submissionintermediate.data.local.entities.StoryEntity
import com.example.submissionintermediate.data.mechanism.NetworkBoundResourceInternet
import com.example.submissionintermediate.data.mechanism.Resource
import com.example.submissionintermediate.data.preferences.UserPreference
import com.example.submissionintermediate.data.remote.RemoteDataSource
import com.example.submissionintermediate.data.remote.network.ApiResponse
import com.example.submissionintermediate.data.remote.response.FileUploadResponse
import com.example.submissionintermediate.data.remote.response.LoginResponse
import com.example.submissionintermediate.data.remote.response.RegisterResponse
import com.example.submissionintermediate.data.remote.response.StoriesResponse
import com.example.submissionintermediate.domain.IDicodingRepository
import com.example.submissionintermediate.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import java.io.File

class DicodingRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val userPreference: UserPreference
) : IDicodingRepository {

    override fun register(
        name: String,
        email: String,
        password: String
    ): Flow<Resource<RegisterResponse>> {
        return object : NetworkBoundResourceInternet<RegisterResponse, RegisterResponse>() {
            override fun loadFromNetwork(data: RegisterResponse): Flow<RegisterResponse> {
                return flowOf(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<RegisterResponse>> =
                remoteDataSource.register(name, email, password)

            override suspend fun saveCallResult(data: RegisterResponse) {

            }

        }.asFlow()
    }

    override fun login(email: String, password: String): Flow<Resource<LoginResponse>> {
        return object : NetworkBoundResourceInternet<LoginResponse, LoginResponse>() {
            override fun loadFromNetwork(data: LoginResponse): Flow<LoginResponse> {
                return flowOf(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<LoginResponse>> =
                remoteDataSource.login(email, password)

            override suspend fun saveCallResult(data: LoginResponse) {
                val result = data.loginResult
                if (result != null) {
                    val user = User(
                        userId = result.userId ?: "",
                        name = result.name ?: "",
                        token = result.token ?: "",
                        isLogin = true
                    )
                    userPreference.saveUser(user)
                }
            }

        }.asFlow()
    }

    override fun getAllStories(): Flow<Resource<StoriesResponse>> {
        return object : NetworkBoundResourceInternet<StoriesResponse, StoriesResponse>() {
            override fun loadFromNetwork(data: StoriesResponse): Flow<StoriesResponse> {
                return flowOf(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<StoriesResponse>> {
                val token = "Bearer " + userPreference.getToken().first()
                return remoteDataSource.getAllStories(token, 1, 10)
            }

            override suspend fun saveCallResult(data: StoriesResponse) {

            }

        }.asFlow()
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagingStories(): Flow<PagingData<StoryEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = StoryRemoteMediator(remoteDataSource, localDataSource, userPreference),
            pagingSourceFactory = {
                localDataSource.getPagingStories()
            }
        ).flow
    }

    override suspend fun getStories(): Flow<ApiResponse<StoriesResponse>> {
        val token = "Bearer " + userPreference.getToken().first()
        return remoteDataSource.getAllStories(token, 1, 5)
    }

    override fun getUser(): Flow<User> {
        return userPreference.getUser()
    }

    override fun addNewStory(file: File, description: String): Flow<Resource<FileUploadResponse>> {
        return object : NetworkBoundResourceInternet<FileUploadResponse, FileUploadResponse>() {
            override fun loadFromNetwork(data: FileUploadResponse): Flow<FileUploadResponse> {
                return flowOf(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<FileUploadResponse>> {
                val token = "Bearer " + userPreference.getToken().first()
                return remoteDataSource.addNewStory(file, description, token)
            }

            override suspend fun saveCallResult(data: FileUploadResponse) {

            }
        }.asFlow()
    }

}