package com.example.submissionintermediate.base

import com.example.submissionintermediate.di.generateTestAppComponent
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

abstract class BaseUITest : KoinTest {
    private lateinit var mockWebServer: MockWebServer

    private var mShouldStart = false


    private fun getMockWebServerUrl() = mockWebServer.url("/").toString()

    @Before
    fun setUp() {
        startMockServer(true)
        loadKoinModules(generateTestAppComponent(getMockWebServerUrl()).toMutableList())
    }

    @After
    fun tearDown() {
        stopMockServer()
        stopKoin()
    }

    fun enqueueResponse(json: String, headers: Map<String, String> = emptyMap()) {
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockResponse.setResponseCode(200).setBody(json)
        mockWebServer.enqueue(mockResponse)
    }

    private fun startMockServer(shouldStart: Boolean) {
        if (shouldStart) {
            mShouldStart = shouldStart
            mockWebServer = MockWebServer()
            mockWebServer.start()
        }
    }

    private fun stopMockServer() {
        if (mShouldStart){
            mockWebServer.shutdown()
        }
    }

}