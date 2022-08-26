package com.example.submissionintermediate.ui.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.submissionintermediate.data.preferences.UserPreference
import com.example.submissionintermediate.model.User
import com.example.submissionintermediate.utils.getOrAwaitValue
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class SplashViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userPreference: UserPreference

    private lateinit var viewModel: SplashViewModel

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = SplashViewModel(userPreference)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `get user success`() = runTest {
        val user = User(userId = "1", name = "hadi", token = "123", isLogin = true)

        `when`(userPreference.getUser()).thenReturn(flowOf(user))

        val actual = viewModel.getUser().getOrAwaitValue()

        verify(userPreference).getUser()

        assertNotNull(actual)
        assertEquals(user, actual)
    }

}

