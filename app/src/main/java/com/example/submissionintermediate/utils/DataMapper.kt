package com.example.submissionintermediate.utils

import com.example.submissionintermediate.data.local.entities.StoryEntity
import com.example.submissionintermediate.data.remote.response.StoriesItem

object DataMapper {

    fun mapStoryItemToStoryEntity(list: List<StoriesItem>): List<StoryEntity> = list.map {
        StoryEntity(
            id = it.id,
            name = it.name,
            description = it.description,
            photoUrl = it.photoUrl,
            createdAt = it.createdAt,
            lat = it.lat ?: 0.0,
            lon = it.lon ?: 0.0
        )
    }
}