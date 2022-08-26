package com.example.submissionintermediate.di

import androidx.room.Room
import com.example.submissionintermediate.BuildConfig
import com.example.submissionintermediate.data.local.LocalDataSource
import com.example.submissionintermediate.data.local.database.DicodingDatabase
import com.example.submissionintermediate.data.remote.RemoteDataSource
import com.example.submissionintermediate.data.remote.network.ApiService
import com.example.submissionintermediate.data.repository.DicodingRepository
import com.example.submissionintermediate.data.repository.StoryRemoteMediator
import com.example.submissionintermediate.domain.IDicodingRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val repositoryModule = module {
    single { RemoteDataSource(get()) }
    single { LocalDataSource(get())}
    single { StoryRemoteMediator(get(), get(),get()) }
    single<IDicodingRepository> { DicodingRepository(get(), get(),get()) }
}

val databaseModule = module {
    factory { get<DicodingDatabase>().storyDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            DicodingDatabase::class.java, "dicoding.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}