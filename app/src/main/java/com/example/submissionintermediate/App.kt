package com.example.submissionintermediate

import android.app.Application
import com.example.submissionintermediate.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

open class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@App)
            modules(provideDependency())
        }
    }

    open fun provideDependency() = listOf(
        databaseModule,
        dataStoreModule,
        viewModelModule,
        networkModule,
        repositoryModule
    )
}