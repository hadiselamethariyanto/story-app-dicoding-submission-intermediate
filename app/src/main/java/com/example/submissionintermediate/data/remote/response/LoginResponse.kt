package com.example.submissionintermediate.data.remote.response

data class LoginResponse(
    val error: Boolean? = null,
    val message: String? = null,
    val loginResult: LoginResultResponse? = null
)

data class LoginResultResponse(
    val userId: String? = null,
    val name: String? = null,
    val token: String? = null
)