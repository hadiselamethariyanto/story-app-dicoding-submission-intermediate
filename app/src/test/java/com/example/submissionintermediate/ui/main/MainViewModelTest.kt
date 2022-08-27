package com.example.submissionintermediate.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.submissionintermediate.data.preferences.UserPreference
import com.example.submissionintermediate.data.repository.DicodingRepository
import com.example.submissionintermediate.model.User
import com.example.submissionintermediate.utils.CoroutineTestRule
import com.example.submissionintermediate.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Mock
    private lateinit var dicodingRepository: DicodingRepository

    @Mock
    private lateinit var userPreference: UserPreference

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = MainViewModel(userPreference, dicodingRepository)
    }


    @Test
    fun `is user login`() = runTest {
        Mockito.`when`(userPreference.getUser()).thenReturn(flowOf(User("1", "hadi", "123", true)))

        viewModel.getUser()

        verify(userPreference).getUser()

        val actual = viewModel.isLogin.getOrAwaitValue()

        Assert.assertNotNull(actual)
        Assert.assertTrue(actual)
    }

    @Test
    fun `is user not login`() = runTest {
        Mockito.`when`(userPreference.getUser()).thenReturn(flowOf(User("", "", "", false)))

        viewModel.getUser()

        verify(userPreference).getUser()

        val actual = viewModel.isLogin.getOrAwaitValue()

        Assert.assertTrue(!actual)

    }

    @Test
    fun `logout success`() = runTest {
        viewModel.logout()

        verify(userPreference).logout()
    }
}