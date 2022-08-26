package com.example.submissionintermediate.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.submissionintermediate.data.preferences.UserPreference
import com.example.submissionintermediate.ui.language.ChangeLanguageViewModel
import com.example.submissionintermediate.ui.login.LoginViewModel
import com.example.submissionintermediate.ui.main.MainViewModel
import com.example.submissionintermediate.ui.register.RegisterViewModel
import com.example.submissionintermediate.ui.splash.SplashViewModel
import com.example.submissionintermediate.ui.upload_story.UploadStoryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { MainViewModel(get(),get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { UploadStoryViewModel(get()) }
    viewModel { ChangeLanguageViewModel(get()) }
}


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val dataStoreModule = module {
    single { UserPreference(androidContext().dataStore) }
}