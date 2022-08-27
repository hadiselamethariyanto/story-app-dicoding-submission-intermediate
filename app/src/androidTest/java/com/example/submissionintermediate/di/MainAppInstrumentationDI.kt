package com.example.submissionintermediate.di

fun generateTestAppComponent(baseApi: String) = listOf(
    configureNetworkForInstrumentationTest(baseApi),
    databaseModule,
    dataStoreModule,
    viewModelModule,
    repositoryModule
)