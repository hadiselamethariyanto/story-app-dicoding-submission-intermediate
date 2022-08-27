package com.example.submissionintermediate.ui.upload_story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.submissionintermediate.data.mechanism.Resource
import com.example.submissionintermediate.data.remote.ApiServiceJson
import com.example.submissionintermediate.data.remote.response.FileUploadResponse
import com.example.submissionintermediate.data.repository.DicodingRepository
import com.example.submissionintermediate.utils.getOrAwaitValue
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class UploadStoryViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Mock
    private lateinit var dicodingRepository: DicodingRepository

    @Mock
    private lateinit var file: File

    private lateinit var viewModel: UploadStoryViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = UploadStoryViewModel(dicodingRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `input data changed success`() = runTest {
        viewModel.dataChanged(file, "test")

        val actual = viewModel.formState.getOrAwaitValue()

        Assert.assertTrue(actual)
    }

    @Test
    fun `input data changed failed`() = runTest {
        viewModel.dataChanged(null, "")

        val actual = viewModel.formState.getOrAwaitValue()

        Assert.assertTrue(!actual)
    }

    @Test
    fun `add new story success`() = runTest {
        viewModel.dataChanged(file, "test")

        val addNewStoryResponse = ApiServiceJson().addStoryResponse()
        val result = Resource.Success(addNewStoryResponse)

        Mockito.`when`(dicodingRepository.addNewStory(file, "test")).thenReturn(flowOf(result))

        val actual = viewModel.addNewStory("test")?.getOrAwaitValue()

        verify(dicodingRepository).addNewStory(file, "test")

        Assert.assertNotNull(actual)
        Assert.assertEquals(result, actual)
    }

    @Test
    fun `add new story failed`() = runTest {
        viewModel.dataChanged(file, "")

        val message = "token expired"
        val result: Resource<FileUploadResponse> = Resource.Error(message, null)

        Mockito.`when`(dicodingRepository.addNewStory(file, "test")).thenReturn(flowOf(result))

        val actual = viewModel.addNewStory("test")?.getOrAwaitValue()

        verify(dicodingRepository).addNewStory(file, "test")

        Assert.assertNotNull(actual)
        Assert.assertEquals(result, actual)
    }

}