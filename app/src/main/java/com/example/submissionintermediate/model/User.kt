package com.example.submissionintermediate.model

data class User(
    val userId: String,
    val name: String,
    val token: String,
    val isLogin: Boolean
)