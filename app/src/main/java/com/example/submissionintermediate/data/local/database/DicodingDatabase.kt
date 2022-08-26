package com.example.submissionintermediate.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.submissionintermediate.data.local.entities.StoryEntity

@Database(entities = [StoryEntity::class], version = 1)
abstract class DicodingDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao
}