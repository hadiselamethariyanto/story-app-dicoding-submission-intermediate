package com.example.submissionintermediate.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import com.example.submissionintermediate.R
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.submissionintermediate.base.BaseUITest
import com.example.submissionintermediate.di.generateTestAppComponent
import com.example.submissionintermediate.utils.ApiServiceJson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

@RunWith(AndroidJUnit4::class)
@MediumTest
class MainActivityTest : BaseUITest() {

    @Before
    fun init() {
        super.setUp()
        loadKoinModules(
            generateTestAppComponent(
                getMockWebServerUrl()
            ).toMutableList()
        )
    }

    @After
    fun finish() {
        super.tearDown()
        unloadKoinModules(
            generateTestAppComponent(
                getMockWebServerUrl()
            ).toMutableList()
        )
    }

    @Test
    fun getStories_Success() {
        ActivityScenario.launch(MainActivity::class.java)

        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when(request.path){
                    ("/stories?page=1&size=10&location=true") ->{
                        MockResponse()
                            .setResponseCode(200)
                            .setBody(ApiServiceJson().getAllStoriesJson())
                    }
                    ("/stories?page=2&size=10&location=true") ->{
                        MockResponse()
                            .setResponseCode(200)
                            .setBody(ApiServiceJson().getSecondPageStoriesJson())
                    }
                    else -> {
                        MockResponse()
                            .setResponseCode(200)
                            .setBody(ApiServiceJson().getEmptyStoriesJson())
                    }
                }
            }
        }

        onView(withId(R.id.rvMain))
            .check(matches(isDisplayed()))

        onView(withText("Dimas-1"))
            .check(matches(isDisplayed()))

        onView(withId(R.id.rvMain)).check { view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            assertEquals(20, recyclerView.adapter?.itemCount)
        }

    }

    @Test
    fun getStories_failed() {
        ActivityScenario.launch(MainActivity::class.java)

        enqueueResponse(ApiServiceJson().getAllStoriesErrorJson(), 401)

        onView(withId(R.id.tvErrorMessage))
            .check(matches(isDisplayed()))

    }

}