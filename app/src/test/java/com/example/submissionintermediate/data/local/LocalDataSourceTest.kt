package com.example.submissionintermediate.data.local

import androidx.paging.PagingSource
import com.example.submissionintermediate.data.local.database.StoryDao
import com.example.submissionintermediate.utils.DataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LocalDataSourceTest {
    private lateinit var storyDao: StoryDao
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        storyDao = FakeStoryDao()
        localDataSource = LocalDataSource(storyDao)
    }

    @Test
    fun `save stories`() = runTest {
        val sampleStories = DataDummy.generateDummyStoriesEntity()
        localDataSource.insertStories(sampleStories)
        val actualStories = localDataSource.getAllStories().first()
        Assert.assertEquals(sampleStories, actualStories)
    }

    @Test
    fun `save then delete stories`() = runTest {
        val sampleStories = DataDummy.generateDummyStoriesEntity()
        localDataSource.insertStories(sampleStories)
        localDataSource.deleteAllStories()
        val actualStories = localDataSource.getAllStories().first()
        Assert.assertTrue(actualStories.isEmpty())
    }

    @Test
    fun `get paging stories`() = runTest {
        val sampleStories = DataDummy.generateDummyStoriesEntity()
        localDataSource.insertStories(sampleStories)
        val actualStories = localDataSource.getPagingStories().load(
            PagingSource.LoadParams.Refresh(
                key = null, loadSize = 10, placeholdersEnabled = false
            )
        )
        Assert.assertEquals((actualStories as? PagingSource.LoadResult.Page)?.data, sampleStories)
    }
}