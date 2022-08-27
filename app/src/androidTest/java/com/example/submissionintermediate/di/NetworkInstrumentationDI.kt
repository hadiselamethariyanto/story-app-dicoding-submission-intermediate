package com.example.submissionintermediate.di

import com.example.submissionintermediate.data.remote.network.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun configureNetworkForInstrumentationTest(baseTestApi: String) = module {
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseTestApi)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}