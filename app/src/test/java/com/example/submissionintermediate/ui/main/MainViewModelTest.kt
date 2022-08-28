package com.example.submissionintermediate.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.submissionintermediate.data.local.entities.StoryEntity
import com.example.submissionintermediate.data.preferences.UserPreference
import com.example.submissionintermediate.data.repository.DicodingRepository
import com.example.submissionintermediate.data.repository.StoryPagingSource
import com.example.submissionintermediate.model.User
import com.example.submissionintermediate.utils.CoroutineTestRule
import com.example.submissionintermediate.utils.DataDummy
import com.example.submissionintermediate.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
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
        `when`(userPreference.getUser()).thenReturn(flowOf(User("1", "hadi", "123", true)))

        viewModel.getUser()

        verify(userPreference).getUser()

        val actual = viewModel.isLogin.getOrAwaitValue()

        Assert.assertNotNull(actual)
        Assert.assertTrue(actual)
    }

    @Test
    fun `is user not login`() = runTest {
        `when`(userPreference.getUser()).thenReturn(flowOf(User("", "", "", false)))

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

    @Test
    fun `get paging stories success`() = runTest {
        val dataDummy = DataDummy.generateDummyStoriesEntity()
        val data: PagingData<StoryEntity> =
            StoryPagingSource.snapshot(dataDummy)

        `when`(dicodingRepository.getPagingStories()).thenReturn(flowOf(data))

        val actual = viewModel.getPagingStories().getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = MainAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actual)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dataDummy, differ.snapshot())
        Assert.assertEquals(dataDummy.size, differ.snapshot().size)
        Assert.assertEquals(dataDummy[0].id, differ.snapshot()[0]?.id)
    }

    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}