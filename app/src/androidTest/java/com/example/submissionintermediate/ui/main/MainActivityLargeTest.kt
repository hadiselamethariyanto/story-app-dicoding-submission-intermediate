package com.example.submissionintermediate.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import com.example.submissionintermediate.R
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.submissionintermediate.base.BaseUITest
import com.example.submissionintermediate.di.generateTestAppComponent
import com.example.submissionintermediate.ui.detail_story.DetailStoryActivity
import com.example.submissionintermediate.ui.language.ChangeLanguageActivity
import com.example.submissionintermediate.utils.ApiServiceJson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityLargeTest : BaseUITest() {

    @Before
    fun init() {
        super.setUp()
        loadKoinModules(
            generateTestAppComponent(
                getMockWebServerUrl()
            ).toMutableList()
        )
        ActivityScenario.launch(MainActivity::class.java)

        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    ("/stories?page=1&size=10&location=true") -> {
                        MockResponse()
                            .setResponseCode(200)
                            .setBody(ApiServiceJson().getAllStoriesJson())
                    }
                    else -> {
                        MockResponse()
                            .setResponseCode(200)
                            .setBody(ApiServiceJson().getEmptyStoriesJson())
                    }
                }
            }
        }
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
    fun loadStories_Success() {
        onView(withId(R.id.rvMain))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rvMain)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                10
            )
        )
    }

    @Test
    fun loadDetailStories_Success(){
        Intents.init()
        onView(withId(R.id.rvMain)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        intended(hasComponent(DetailStoryActivity::class.java.name))
        onView(withId(R.id.imgPhoto)).check(matches(isDisplayed()))
        onView(withId(R.id.imgUser)).check(matches(isDisplayed()))
        onView(withId(R.id.tvFullName)).check(matches(withText("Dimas-1")))
        onView(withId(R.id.tvCreatedAt)).check(matches(withText("231 days ago")))
        onView(withId(R.id.tvDescription)).check(matches(withText("Lorem Ipsum")))
    }

    @Test
    fun goTo_changeLanguage(){
        Intents.init()
        onView(withId(R.id.language)).perform(click())
        intended(hasComponent(ChangeLanguageActivity::class.java.name))
        onView(withId(R.id.rvLanguage)).check(matches(isDisplayed()))
        onView(withId(R.id.rvLanguage)).check { view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            Assert.assertEquals(3, recyclerView.adapter?.itemCount)
        }
    }

    @Test
    fun changeLanguage_toDutch_Success(){
        Intents.init()
        onView(withId(R.id.language)).perform(click())
        intended(hasComponent(ChangeLanguageActivity::class.java.name))
        onView(withId(R.id.rvLanguage)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
        onView(withId(R.id.language)).perform(click())
        onView(withText("Taal Veranderen")).check(matches(isDisplayed()))
    }

}