package com.example.submissionintermediate.ui.main

import android.os.SystemClock
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import com.example.submissionintermediate.R
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.submissionintermediate.base.BaseUITest
import com.example.submissionintermediate.utils.ApiServiceJson
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class MainActivityTest : BaseUITest() {

    @Test
    fun getStories_Success() {
        ActivityScenario.launch(MainActivity::class.java)

        enqueueResponse(ApiServiceJson().getAllStoriesJson())

        SystemClock.sleep(1000)

        onView(withId(R.id.rvMain))
            .check(matches(isDisplayed()))

        onView(withText("Dimas"))
            .check(matches(isDisplayed()))

        onView(withId(R.id.rvMain))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Lorem Ipsum"))
                )
            )
    }

    @Test
    fun getStories_failed(){

    }


}