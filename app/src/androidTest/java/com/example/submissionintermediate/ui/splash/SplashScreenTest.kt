package com.example.submissionintermediate.ui.splash

import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.submissionintermediate.R
import com.example.submissionintermediate.ui.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashScreenTest{
    @get:Rule
    var activityTestRule = ActivityScenarioRule(SplashScreen::class.java)

    @Test
    fun splash(){
        onView(withId(R.id.imgSplash))
            .check(matches(isDisplayed()))
    }


}