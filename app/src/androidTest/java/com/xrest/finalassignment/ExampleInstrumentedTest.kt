package com.xrest.finalassignment

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.xrest.finalassignment.Adapter.FoodAdapter
import com.xrest.finalassignment.Retrofit.RetrofitBuilder
import com.xrest.finalassignment.Retrofit.UserRepo
import com.xrest.finalassignment.UI.Dashboard
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class Test {


    var userRepository = UserRepo()

    @get:Rule
    val main = ActivityScenarioRule(Dashboard::class.java)

    @Test
    fun bookUpdate() {

        runBlocking {
            var userRepository = UserRepo()
            RetrofitBuilder.token = "Bearer " + userRepository.Login("123", "123").token
            RetrofitBuilder.user = userRepository.Login("123", "123").data
        }
        Espresso.onView(ViewMatchers.withId(R.id.navigation_dashboard))
            .perform(ViewActions.click())
        Thread.sleep(400)

        Espresso.onView(ViewMatchers.withId(R.id.rvx)).perform(
            RecyclerViewActions.actionOnItemAtPosition<FoodAdapter.FoodHolder>(
                0,
                clickOnViewChild(R.id.delete)
            )
        )


    }
    private fun clickOnViewChild(viewId: Int) = object : ViewAction {
        override fun getConstraints() = null

        override fun getDescription() = "Click on a child view with specified id."

        override fun perform(uiController: UiController, view: View) = ViewActions.click()
            .perform(uiController, view.findViewById<View>(viewId))
    }
}
