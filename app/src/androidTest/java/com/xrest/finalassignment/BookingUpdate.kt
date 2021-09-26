package com.xrest.finalassignment

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.xrest.finalassignment.Adapter.FoodAdapter
import com.xrest.finalassignment.Retrofit.RetrofitBuilder
import com.xrest.finalassignment.Retrofit.UserRepo
import com.xrest.finalassignment.UI.Dashboard
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)

class BookingUpdate {
    var userRepository = UserRepo()
    @get:Rule
    val main = ActivityScenarioRule(Dashboard::class.java)

    @Test
    fun bookUpdate(){

        runBlocking {
            var userRepository = UserRepo()
            RetrofitBuilder.token = "Bearer " + userRepository.Login("123", "123").token
            RetrofitBuilder.user = userRepository.Login("123", "123").data
        }
        onView(withId(R.id.navigation_cart))
            .perform(ViewActions.click())
        Thread.sleep(400)

        onView(withId(R.id.rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<FoodAdapter.FoodHolder>(
                0,
                clickOnViewChild(R.id.plus)
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



