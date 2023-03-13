@file:OptIn(ExperimentalCoroutinesApi::class)

package me.syahdilla.putra.sholeh.storyappdicoding.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import me.syahdilla.putra.sholeh.storyappdicoding.*
import me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.login.LoginActivity
import me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.main.MainActivity
import me.syahdilla.putra.sholeh.storyappdicoding.utils.UIIdlingResourceTest
import me.syahdilla.putra.sholeh.storyappdicoding.utils.waitUntil
import org.junit.Assert.*
import org.junit.Test

class LoginActivityTest: UIIdlingResourceTest<LoginActivity>(LoginActivity::class) {


    @Test
    fun loginAndLogoutUserTest() = runTest {
        Intents.init()
        onView(withId(R.id.ed_login_email)).check(matches(isDisplayed()))
        onView(withId(R.id.ed_login_email)).perform(text("user1.test@gmail.com"))
        onView(withId(R.id.ed_login_password)).perform(text("12345678"))
        onView(withId(R.id.btnLogin)).perform(click())
        waitUntil {
            intended(hasComponent(MainActivity::class.java.name))
        }
        onView(withId(R.id.action_logout)).perform(click())
        waitUntil {
            intended(hasComponent(LoginActivity::class.java.name))
        }
    }

    @Test
    fun signupTestUser() = runTest {
        onView(withId(R.id.btnSignup)).perform(click())
        waitUntil {
            onView(withId(R.id.labelSignUp)).check(matches(isDisplayed()))
        }
        onView(withId(R.id.ed_register_name)).perform(text("Testing 123"))
        onView(withId(R.id.ed_register_name)).perform(text("user2.test1@gmail.com"))
        onView(withId(R.id.ed_register_password)).perform(text("12345678"))
        onView(withId(R.id.btn_signup)).perform(click())
    }

}