package com.example.submissionintermediate.utils

import com.example.submissionintermediate.data.local.entities.StoryEntity

object DataDummy {
    fun generateDummyStoriesEntity(): List<StoryEntity> {
        val stories = ArrayList<StoryEntity>()
        for (i in 0..10) {
            val news = StoryEntity(
                "$i",
                "name $i",
                "description $i",
                "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
                "2022-02-22T22:22:22Z",
                1.0,
                1.0,
            )
            stories.add(news)
        }
        return stories
    }
}