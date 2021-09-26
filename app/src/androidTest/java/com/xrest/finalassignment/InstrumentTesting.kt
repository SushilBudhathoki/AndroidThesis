package com.xrest.finalassignment

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.xrest.finalassignment.UI.Dashboard
import com.xrest.finalassignment.UI.Login
import com.xrest.finalassignment.UI.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class InstrumentTesting {




    @get:Rule
    val main = ActivityScenarioRule(MainActivity::class.java)
    @Test
    fun register(){
        Espresso.onView(ViewMatchers.withId(R.id.etFname))
            .perform(ViewActions.typeText("sonam"))
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.etLname))
            .perform(ViewActions.typeText("sonam"))
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.etPhone))
            .perform(ViewActions.typeText("8888888888"))
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.etUsername))
            .perform(ViewActions.typeText("456"))
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.etPassword))
            .perform(ViewActions.typeText("456"))
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.etCPassword))
            .perform(ViewActions.typeText("456"))
        Thread.sleep(1000)
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.btnAddStudent))
            .perform(ViewActions.click())
        Thread.sleep(4000)
        Espresso.
        onView(withId(R.id.uilogin))
            .check(matches(isDisplayed()))

    }






}

