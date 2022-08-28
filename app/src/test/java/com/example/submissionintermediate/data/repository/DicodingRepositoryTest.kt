package com.example.submissionintermediate.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.submissionintermediate.data.local.LocalDataSource
import com.example.submissionintermediate.data.local.PagingSourceUtils
import com.example.submissionintermediate.data.preferences.UserPreference
import com.example.submissionintermediate.data.remote.ApiServiceJson
import com.example.submissionintermediate.data.remote.RemoteDataSource
import com.example.submissionintermediate.data.remote.network.ApiResponse
import com.example.submissionintermediate.model.User
import com.example.submissionintermediate.utils.DataDummy
import com.example.submissionintermediate.utils.DataMapper
import com.example.submissionintermediate.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import java.io.File


@OptIn(ExperimentalCoroutinesApi::class)
class DicodingRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val remoteDataSource = mock(RemoteDataSource::class.java)
    private val localDataSource = mock(LocalDataSource::class.java)
    private val userPreference = mock(UserPreference::class.java)
    private val repository = DicodingRepository(remoteDataSource, localDataSource, userPreference)

    @Test
    fun `register success`() = runTest {
        val registerResponse = ApiServiceJson().registerResponse(ApiServiceJson().registerJson())
        `when`(remoteDataSource.register(anyString(), anyString(), anyString())).thenReturn(
            flowOf(ApiResponse.Success(registerResponse))
        )

        val actual = repository.register("hadi", "hadi@gmail.com", "hadi").toList()
        val inOrder = inOrder(remoteDataSource)
        inOrder.verify(remoteDataSource).register("hadi", "hadi@gmail.com", "hadi")

        Assert.assertNotNull(actual)
        Assert.assertEquals(actual[1].data, registerResponse)
    }

    @Test
    fun `register input empty`() = runTest {
        val registerResponse =
            ApiServiceJson().registerResponse(ApiServiceJson().registerErrorJson())
        `when`(remoteDataSource.register(anyString(), anyString(), anyString())).thenReturn(
            flowOf(ApiResponse.Error(errorMessage = registerResponse.message ?: ""))
        )

        val actual = repository.register("", "", "").toList()
        val inOrder = inOrder(remoteDataSource)
        inOrder.verify(remoteDataSource).register("", "", "")

        Assert.assertNotNull(actual)
        Assert.assertEquals(registerResponse.message, actual[1].message)
    }

    @Test
    fun `login success`() = runTest {
        val loginResponse = ApiServiceJson().loginResponse()
        val result = loginResponse.loginResult
        `when`(
            remoteDataSource.login(
                anyString(),
                anyString()
            )
        ).thenReturn(flowOf(ApiResponse.Success(loginResponse)))

        val actual = repository.login("", "").toList()
        val inOrder = inOrder(remoteDataSource, userPreference)
        inOrder.verify(remoteDataSource).login("", "")
        inOrder.verify(userPreference).saveUser(
            User(
                userId = result?.userId ?: "",
                name = result?.name ?: "",
                token = result?.token ?: "",
                isLogin = true
            )
        )

        Assert.assertNotNull(actual)
        Assert.assertEquals(loginResponse.message, actual[1].data?.message)
        Assert.assertEquals(loginResponse.loginResult, actual[1].data?.loginResult)
        Assert.assertEquals(loginResponse.error, actual[1].data?.error)
    }

    @Test
    fun `login failed`() = runTest {
        val errorMessage = "wrong password"
        `when`(
            remoteDataSource.login(
                anyString(),
                anyString()
            )
        ).thenReturn(flowOf(ApiResponse.Error(errorMessage)))

        val actual = repository.login("", "").toList()
        val inOrder = inOrder(remoteDataSource)
        inOrder.verify(remoteDataSource).login("", "")

        Assert.assertNotNull(actual)
        Assert.assertEquals(errorMessage, actual[1].message)
    }

    @Test
    fun `get all stories success`() = runTest {
        val token = "123"
        val page = 1
        val size = 10

        val storiesResponse = ApiServiceJson().getAllStoriesResponse()

        `when`(userPreference.getToken()).thenReturn(flowOf(token))

        `when`(remoteDataSource.getAllStories("Bearer $token", page, size)).thenReturn(
            flowOf(
                ApiResponse.Success(storiesResponse)
            )
        )

        val actual = repository.getAllStories().toList()

        val inOrder = inOrder(userPreference, remoteDataSource)
        inOrder.verify(userPreference).getToken()
        inOrder.verify(remoteDataSource).getAllStories("Bearer $token", page, size)

        Assert.assertNotNull(actual)
        Assert.assertEquals(storiesResponse.message, actual[1].data?.message)
        Assert.assertEquals(storiesResponse.listStory, actual[1].data?.listStory)
        Assert.assertEquals(storiesResponse.error, actual[1].data?.error)
    }

    @Test
    fun `get all stories failed`() = runTest {
        val token = "123"
        val page = 1
        val size = 10

        val storiesResponse = ApiServiceJson().getAllStoriesResponse()

        `when`(userPreference.getToken()).thenReturn(flowOf(token))

        `when`(remoteDataSource.getAllStories("Bearer $token", page, size)).thenReturn(
            flowOf(
                ApiResponse.Success(storiesResponse)
            )
        )

        val actual = repository.getAllStories().toList()

        val inOrder = inOrder(userPreference, remoteDataSource)
        inOrder.verify(userPreference).getToken()
        inOrder.verify(remoteDataSource).getAllStories("Bearer $token", page, size)

        Assert.assertNotNull(actual)
        Assert.assertEquals(storiesResponse.message, actual[1].data?.message)
        Assert.assertEquals(storiesResponse.listStory, actual[1].data?.listStory)
        Assert.assertEquals(storiesResponse.error, actual[1].data?.error)
    }

    @Test
    fun `add new story success`() = runTest {
        val token = "123"
        val file = mock(File::class.java)
        val description = "deskripsi"

        val addStoryResponse = ApiServiceJson().addStoryResponse()

        `when`(userPreference.getToken()).thenReturn(flowOf(token))

        `when`(
            remoteDataSource.addNewStory(
                file,
                description,
                "Bearer $token"
            )
        ).thenReturn(flowOf(ApiResponse.Success(addStoryResponse)))

        val actual = repository.addNewStory(file, description).toList()
        val inOrder = inOrder(userPreference, remoteDataSource)
        inOrder.verify(userPreference).getToken()
        inOrder.verify(remoteDataSource).addNewStory(file, description, "Bearer $token")
        Assert.assertNotNull(actual)
        Assert.assertEquals(addStoryResponse.message, actual[1].data?.message)
        Assert.assertEquals(addStoryResponse.error, actual[1].data?.error)
    }

    @Test
    fun `add new story failed`() = runTest {
        val token = "123"
        val file = mock(File::class.java)
        val description = "deskripsi"

        val message = "file more than 5 mb"

        `when`(userPreference.getToken()).thenReturn(flowOf(token))

        `when`(
            remoteDataSource.addNewStory(
                file,
                description,
                "Bearer $token"
            )
        ).thenReturn(flowOf(ApiResponse.Error(message)))

        val actual = repository.addNewStory(file, description).toList()
        val inOrder = inOrder(userPreference, remoteDataSource)
        inOrder.verify(userPreference).getToken()
        inOrder.verify(remoteDataSource).addNewStory(file, description, "Bearer $token")
        Assert.assertNotNull(actual)
        Assert.assertEquals(message, actual[1].message)
    }


    @Test
    fun `get user`() = runTest {
        val user = User(userId = "1", name = "hadi", token = "123", isLogin = true)

        `when`(userPreference.getUser()).thenReturn(flowOf(user))

        val actual = repository.getUser().first()

        verify(userPreference).getUser()

        Assert.assertEquals(user, actual)
    }

    @Test
    fun `get all stories from local success`() = runTest {
        val storiesResponse = ApiServiceJson().getAllStoriesResponse()
        val storyEntities = DataMapper.mapStoryItemToStoryEntity(storiesResponse.listStory)

        `when`(localDataSource.getAllStories()).thenReturn(flowOf(storyEntities))

        val actual = repository.getAllStoriesFromLocal().first()

        verify(localDataSource).getAllStories()

        Assert.assertNotNull(actual)
        Assert.assertEquals(storyEntities, actual)
    }

    @Test
    fun `get stories success`() = runTest {
        val token = "123"
        val storiesResponse = ApiServiceJson().getAllStoriesResponse()

        `when`(userPreference.getToken()).thenReturn(flowOf(token))
        `when`(
            remoteDataSource.getAllStories(
                "Bearer $token",
                1,
                5
            )
        ).thenReturn(flowOf(ApiResponse.Success(storiesResponse)))

        val actual = repository.getStories().first()

        val inOrder = inOrder(userPreference, remoteDataSource)
        inOrder.verify(userPreference).getToken()
        inOrder.verify(remoteDataSource).getAllStories(
            "Bearer $token",
            1,
            5
        )
        Assert.assertNotNull(actual)
        Assert.assertEquals(ApiResponse.Success(storiesResponse), actual)
    }

    @Test
    fun `get story paging source success`() = runTest {
        val stories = DataDummy.generateDummyStoriesEntity()
        `when`(localDataSource.getPagingStories()).thenReturn(PagingSourceUtils(stories))

        val actual = repository.getPagingStories().first()

        Assert.assertNotNull(actual)
    }
}