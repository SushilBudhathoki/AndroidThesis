package com.xrest.finalassignment

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.xrest.finalassignment.UI.Login
import org.junit.Rule
import org.junit.Test

class LoginTesting {
    @get:Rule
    val testRule = ActivityScenarioRule(Login::class.java)
    @Test
    fun checkArithmeticUI() {
        Espresso.onView(ViewMatchers.withId(R.id.name))
            .perform(ViewActions.typeText("sonam"))
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.password))
            .perform(ViewActions.typeText("sonam"))
        Thread.sleep(1000)
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.login))
            .perform(ViewActions.click())
        Thread.sleep(4000)
        Espresso.
        onView(ViewMatchers.withId(R.id.fl))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}