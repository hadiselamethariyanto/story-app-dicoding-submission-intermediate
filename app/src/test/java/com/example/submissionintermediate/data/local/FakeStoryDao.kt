package com.example.submissionintermediate.data.local

import androidx.paging.PagingSource
import com.example.submissionintermediate.data.local.database.StoryDao
import com.example.submissionintermediate.data.local.entities.StoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeStoryDao : StoryDao {
    private var stories = mutableListOf<StoryEntity>()

    override suspend fun insertStories(stories: List<StoryEntity>) {
        this.stories.addAll(stories)
    }

    override suspend fun deleteAll() {
        this.stories.clear()
    }

    override fun getPagingStories(): PagingSource<Int, StoryEntity> {
        return PagingSourceUtils(stories)
    }

    override fun getAllStories(): Flow<List<StoryEntity>> {
        return flowOf(stories)
    }

}