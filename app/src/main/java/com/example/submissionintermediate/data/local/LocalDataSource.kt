package com.example.submissionintermediate.data.local

import com.example.submissionintermediate.data.local.database.StoryDao
import com.example.submissionintermediate.data.local.entities.StoryEntity

class LocalDataSource(private val storyDao: StoryDao) {

    suspend fun insertStories(stories: List<StoryEntity>) = storyDao.insertStories(stories)

    suspend fun deleteAllStories() = storyDao.deleteAll()

    fun getPagingStories() = storyDao.getPagingStories()

    fun getAllStories() = storyDao.getAllStories()
}