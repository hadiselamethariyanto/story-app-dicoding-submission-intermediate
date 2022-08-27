package com.example.submissionintermediate.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.submissionintermediate.data.mechanism.Resource
import com.example.submissionintermediate.data.remote.ApiServiceJson
import com.example.submissionintermediate.data.remote.response.LoginResponse
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
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dicodingRepository: DicodingRepository

    private lateinit var viewModel: LoginViewModel

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = LoginViewModel(dicodingRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `login success`() = runTest {
        val loginResponse = ApiServiceJson().loginResponse()
        val result = Resource.Success(loginResponse)

        `when`(dicodingRepository.login(anyString(), anyString())).thenReturn(
            flowOf(result)
        )

        val actual = viewModel.login("a@gmail.com", "123").getOrAwaitValue()

        verify(dicodingRepository).login("a@gmail.com", "123")

        Assert.assertNotNull(actual)
        Assert.assertEquals(result, actual)
    }

    @Test
    fun `login failed`() = runTest {
        val message = "wrong password"
        val result: Resource<LoginResponse> = Resource.Error(message, null)

        `when`(dicodingRepository.login(anyString(), anyString())).thenReturn(
            flowOf(result)
        )

        val actual = viewModel.login("a@gmail.com", "123").getOrAwaitValue()

        verify(dicodingRepository).login("a@gmail.com", "123")

        Assert.assertNotNull(actual)
        Assert.assertEquals(result, actual)
    }

    @Test
    fun `input changed`() = runTest {
        viewModel.dataChanged(email = true, password = true)

        val formState = viewModel.formState.getOrAwaitValue()

        Assert.assertTrue(formState)
    }

}