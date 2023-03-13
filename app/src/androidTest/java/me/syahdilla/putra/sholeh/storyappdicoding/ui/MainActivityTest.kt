@file:OptIn(ExperimentalCoroutinesApi::class)

package me.syahdilla.putra.sholeh.storyappdicoding.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import me.syahdilla.putra.sholeh.storyappdicoding.R
import me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.main.MainActivity
import me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.storydetails.StoryDetailsActivity
import me.syahdilla.putra.sholeh.storyappdicoding.utils.UIIdlingResourceTest
import org.junit.Test

class MainActivityTest: UIIdlingResourceTest<MainActivity>(MainActivity::class) {

    @Test
    fun loadAndClickStoryList_Success() = runTest {
        Intents.init()
        onView(withId(R.id.storyListRV)).check(matches(isDisplayed()))
        onView(withId(R.id.storyListRV)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        intended(hasComponent(StoryDetailsActivity::class.java.name))
        pressBack()
    }

}