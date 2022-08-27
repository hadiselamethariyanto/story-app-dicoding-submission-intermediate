package com.example.submissionintermediate.base

import androidx.test.espresso.IdlingRegistry
import com.example.submissionintermediate.utils.EspressoIdlingResource
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.test.KoinTest

abstract class BaseUITest : KoinTest {
    lateinit var mockWebServer: MockWebServer

    private var mShouldStart = false


    fun getMockWebServerUrl() = mockWebServer.url("/").toString()

    @Before
    fun setUp() {
        startMockServer(true)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        stopMockServer()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    fun enqueueResponse(json: String, responseCode: Int) {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(responseCode).setBody(json)
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
        if (mShouldStart) {
            mockWebServer.shutdown()
        }
    }

}