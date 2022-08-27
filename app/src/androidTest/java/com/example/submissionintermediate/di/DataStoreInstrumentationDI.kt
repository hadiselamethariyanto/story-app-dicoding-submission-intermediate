package com.example.submissionintermediate.di

import com.example.submissionintermediate.data.preferences.UserPreference
import org.koin.dsl.module

fun configureDataStoreModule(userPreference: UserPreference) = module {
    single { userPreference }
}