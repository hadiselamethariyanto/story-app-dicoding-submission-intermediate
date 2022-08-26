package com.example.submissionintermediate.data.local.database

import androidx.paging.PagingSource
import androidx.room.*
import com.example.submissionintermediate.data.local.entities.StoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStories(stories: List<StoryEntity>)

    @Query("DELETE FROM story")
    suspend fun deleteAll()

    @Query("SELECT * FROM story")
    fun getPagingStories(): PagingSource<Int, StoryEntity>

    @Query("SELECT * FROM story")
    fun getAllStories(): Flow<List<StoryEntity>>
}