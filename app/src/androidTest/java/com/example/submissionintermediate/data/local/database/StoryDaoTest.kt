package com.example.submissionintermediate.data.local.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.submissionintermediate.utils.DataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.*

@OptIn(ExperimentalCoroutinesApi::class)
class StoryDaoTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: DicodingDatabase
    private lateinit var dao: StoryDao
    private val sampleStory = DataDummy.generateDummyStoriesEntity()

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DicodingDatabase::class.java
        ).build()
        dao = database.storyDao()
    }

    @Test
    fun saveStories_Success() = runTest {
        dao.insertStories(sampleStory)
        val stories = dao.getAllStories().first()
        Assert.assertEquals(sampleStory.size, stories.size)
    }

    @Test
    fun deleteStories_Success() = runTest {
        dao.insertStories(sampleStory)
        dao.deleteAll()
        val stories = dao.getAllStories().first()
        Assert.assertTrue(stories.isEmpty())
    }


    @After
    fun closeDb() = database.close()
}