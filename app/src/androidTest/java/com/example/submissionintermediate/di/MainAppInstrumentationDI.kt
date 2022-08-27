package com.example.submissionintermediate.di

fun generateTestAppComponent(baseApi: String) = listOf(
    configureNetworkForInstrumentationTest(baseApi),
    viewModelModule,
    databaseModule,
    dataStoreModule,
    repositoryModule
)