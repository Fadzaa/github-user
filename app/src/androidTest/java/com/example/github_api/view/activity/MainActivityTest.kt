package com.example.github_api.view.activity


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.github_api.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val searchBar = onView(
            allOf(
                withId(R.id.searchBar),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    11
                ),
                isDisplayed()
            )
        )
        searchBar.perform(click())

        val editText = onView(
            allOf(
                withId(com.google.android.material.R.id.search_view_edit_text),
                childAtPosition(
                    childAtPosition(
                        withId(com.google.android.material.R.id.search_view_toolbar),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        editText.perform(replaceText("dicoding"), closeSoftKeyboard())

        val editText2 = onView(
            allOf(
                withId(com.google.android.material.R.id.search_view_edit_text),
                withText("dicoding"),
                childAtPosition(
                    childAtPosition(
                        withId(com.google.android.material.R.id.search_view_toolbar),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        editText2.perform(pressImeActionButton())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
