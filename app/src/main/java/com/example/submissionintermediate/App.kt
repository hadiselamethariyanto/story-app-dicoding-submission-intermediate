package com.example.submissionintermediate

import android.app.Application
import com.example.submissionintermediate.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@App)
            modules(
                listOf(
                    databaseModule,
                    dataStoreModule,
                    viewModelModule,
                    networkModule,
                    repositoryModule
                )
            )
        }
    }
}