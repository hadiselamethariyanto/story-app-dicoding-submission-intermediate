package com.example.submissionintermediate.data.remote

import com.example.submissionintermediate.data.remote.network.ApiResponse
import com.example.submissionintermediate.data.remote.network.ApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response
import java.lang.RuntimeException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class RemoteDataSourceTest {

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp() {
        remoteDataSource = RemoteDataSource(apiService)
    }

    @Test
    fun `get stories success `() = runTest {
        val storiesResponse = ApiServiceJson().getAllStoriesResponse()
        `when`(apiService.getAllStories(1, 10, true, "123")).thenReturn(storiesResponse)

        val storiesActual = remoteDataSource.getAllStories("123", 1, 10).first()
        verify(apiService).getAllStories(1, 10, true, "123")
        Assert.assertEquals(storiesActual, ApiResponse.Success(storiesResponse))
    }

    @Test
    fun `get stories failed`() = runTest {
        val exception = HttpException(Response.error<ResponseBody>(500 ,ResponseBody.create(
            "plain/text".toMediaTypeOrNull(),"{\n" +
                    "    \"error\": true,\n" +
                    "    \"message\": \"token expired\"\n" +
                    "}")))
        `when`(apiService.getAllStories(anyInt(), anyInt(), anyBoolean(), anyString())).thenThrow(exception)
        val actual = remoteDataSource.getAllStories("", 0, 0).first()
        verify(apiService).getAllStories(anyInt(), anyInt(), anyBoolean(), anyString())

        Assert.assertEquals(ApiResponse.Error("token expired"),actual)
    }

    @Test
    fun `get stories no internet connection`() = runTest {
        val message = "No internet connection"
        `when`(apiService.getAllStories(anyInt(), anyInt(), anyBoolean(), anyString())).thenThrow(RuntimeException(message))
        val actual = remoteDataSource.getAllStories("", 0, 0).first()
        verify(apiService).getAllStories(anyInt(), anyInt(), anyBoolean(), anyString())

        Assert.assertEquals(ApiResponse.Error(message),actual)
    }

    @Test
    fun `register success`() = runTest {
        val registerResponse = ApiServiceJson().registerResponse(ApiServiceJson().registerJson())
        `when`(apiService.register(anyString(), anyString(), anyString())).thenReturn(registerResponse)

        val registerActual = remoteDataSource.register("", "", "").first()
        verify(apiService).register("", "", "")
        Assert.assertEquals(registerActual, ApiResponse.Success(registerResponse))
    }

    @Test
    fun `register failed`() = runTest {
        val exception = HttpException(Response.error<ResponseBody>(500 ,ResponseBody.create(
            "plain/text".toMediaTypeOrNull(),"{\n" +
                    "    \"error\": true,\n" +
                    "    \"message\": \"email empty\"\n" +
                    "}")))
        `when`(apiService.register(anyString(), anyString(), anyString())).thenThrow(exception)
        val actual = remoteDataSource.register("", "", "").first()
        verify(apiService).register("", "", "")

        Assert.assertEquals(ApiResponse.Error("email empty"),actual)
    }

    @Test
    fun `register no internet connection`() = runTest {
        val message = "No internet connection"
        `when`(apiService.register(anyString(), anyString(), anyString())).thenThrow(RuntimeException(message))
        val actual = remoteDataSource.register("", "", "").first()
        verify(apiService).register("", "", "")

        Assert.assertEquals(ApiResponse.Error(message),actual)
    }

    @Test
    fun `login success`() = runTest {
        val loginResponse = ApiServiceJson().loginResponse()
        `when`(apiService.login(anyString(), anyString())).thenReturn(loginResponse)

        val actual = remoteDataSource.login("", "").first()
        verify(apiService).login("", "")
        Assert.assertEquals(actual, ApiResponse.Success(loginResponse))
    }

    @Test
    fun `login failed`() = runTest {
        val exception = HttpException(Response.error<ResponseBody>(500 ,ResponseBody.create(
            "plain/text".toMediaTypeOrNull(),"{\n" +
                    "    \"error\": true,\n" +
                    "    \"message\": \"email empty\"\n" +
                    "}")))
        `when`(apiService.login(anyString(), anyString())).thenThrow(exception)
        val actual = remoteDataSource.login("", "").first()
        verify(apiService).login("", "")

        Assert.assertEquals(ApiResponse.Error("email empty"),actual)
    }

    @Test
    fun `login no internet connection`() = runTest {
        val message = "No internet connection"
        `when`(apiService.login(anyString(), anyString())).thenThrow(RuntimeException(message))
        val actual = remoteDataSource.login("", "").first()
        verify(apiService).login("", "")

        Assert.assertEquals(ApiResponse.Error(message),actual)
    }

//    @Test
//    fun `add stories success`() = runTest {
//        val fileUploadResponse = ApiServiceJson().addStoryResponse()
//        val file = mock(File::class.java)
//        val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
//        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
//            "photo",
//            file.name,
//            requestImageFile
//        )
//        `when`(apiService.addNewStory(imageMultipart, "123".toRequestBody("text/plain".toMediaType()),
//            "123")).thenReturn(fileUploadResponse)
//
//        val actual = remoteDataSource.addNewStory(file, "123","123").first()
////        verify(apiService).addNewStory(imageMultipart, anyString().toRequestBody("text/plain".toMediaType()),
////            anyString())
//        Assert.assertEquals(ApiResponse.Success(fileUploadResponse),actual)
//    }

}