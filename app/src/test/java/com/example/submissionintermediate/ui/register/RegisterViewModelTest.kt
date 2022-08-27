package com.example.submissionintermediate.ui.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.submissionintermediate.data.mechanism.Resource
import com.example.submissionintermediate.data.remote.ApiServiceJson
import com.example.submissionintermediate.data.remote.response.RegisterResponse
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

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dicodingRepository: DicodingRepository

    private lateinit var viewModel: RegisterViewModel

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = RegisterViewModel(dicodingRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `register success`() = runTest {
        val registerResponse = ApiServiceJson().registerResponse(ApiServiceJson().registerJson())
        val result: Resource<RegisterResponse> = Resource.Success(registerResponse)

        Mockito.`when`(
            dicodingRepository.register(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString()
            )
        ).thenReturn(
            flowOf(result)
        )

        val actual = viewModel.register("me", "me@gmail.com", "123").getOrAwaitValue()

        verify(dicodingRepository).register("me", "me@gmail.com", "123")

        Assert.assertNotNull(actual)
        Assert.assertEquals(result, actual)
    }

    @Test
    fun `register failed`() = runTest {
        val message = "email existed"
        val result: Resource<RegisterResponse> = Resource.Error(message, data = null)

        Mockito.`when`(
            dicodingRepository.register(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString()
            )
        ).thenReturn(
            flowOf(result)
        )

        val actual = viewModel.register("me", "me@gmail.com", "123").getOrAwaitValue()

        verify(dicodingRepository).register("me", "me@gmail.com", "123")

        Assert.assertNotNull(actual)
        Assert.assertEquals(result, actual)
    }

    @Test
    fun `input changed success`()= runTest {
        viewModel.dataChanged("me@gmail.com","me","123456")

        val actual = viewModel.formState.getOrAwaitValue()

        Assert.assertTrue(actual)
    }

    @Test
    fun `input changed failed`() = runTest {
        viewModel.dataChanged("","","")

        val actual = viewModel.formState.getOrAwaitValue()

        Assert.assertTrue(!actual)
    }

}