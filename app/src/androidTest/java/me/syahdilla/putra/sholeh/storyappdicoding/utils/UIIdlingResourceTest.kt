package me.syahdilla.putra.sholeh.storyappdicoding.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import kotlin.reflect.KClass


@RunWith(AndroidJUnit4::class)
@LargeTest
abstract class UIIdlingResourceTest<T: AppCompatActivity>(
    clazz: KClass<T>
) {

    @get:Rule
    val activity = ActivityScenarioRule(clazz.java)

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        onSetup()
    }

    open fun onSetup() {}

    @After
    fun onDone() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

}