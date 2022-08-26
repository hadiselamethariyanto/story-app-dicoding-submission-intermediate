package com.example.submissionintermediate.data.remote.response

data class StoriesResponse(
    val error: Boolean,
    val message: String,
    val listStory: List<StoriesItem>
)

data class StoriesItem(
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String,
    val createdAt: String,
    val lat: Double,
    val lon: Double
)