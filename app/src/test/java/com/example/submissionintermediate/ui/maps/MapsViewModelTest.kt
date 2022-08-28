package com.example.submissionintermediate.ui.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.submissionintermediate.data.remote.ApiServiceJson
import com.example.submissionintermediate.data.repository.DicodingRepository
import com.example.submissionintermediate.utils.DataMapper
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
import org.mockito.junit.MockitoJUnitRunner


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dicodingRepository: DicodingRepository

    private lateinit var viewModel: MapsViewModel

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = MapsViewModel(dicodingRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `get stories from local success`() = runTest {
        val storiesResponse = ApiServiceJson().getAllStoriesResponse()
        val storyEntities = DataMapper.mapStoryItemToStoryEntity(storiesResponse.listStory)

        Mockito.`when`(dicodingRepository.getAllStoriesFromLocal())
            .thenReturn(flowOf(storyEntities))

        val actual = viewModel.getAllStoriesFromLocal().getOrAwaitValue()

        Mockito.verify(dicodingRepository).getAllStoriesFromLocal()

        Assert.assertNotNull(actual)
        Assert.assertEquals(storyEntities, actual)
    }
}