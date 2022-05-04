package com.example.mybooks.utils

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun waitFor(delay: Long): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> = ViewMatchers.isRoot()
        override fun getDescription(): String = "wait for $delay milliseconds"
        override fun perform(uiController: UiController, v: View?) {
            uiController.loopMainThreadForAtLeast(delay)
        }
    }
}

fun hasTextInputLayoutErrorText(expectedErrorText: String?): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        override fun matchesSafely(view: View?): Boolean {
            if (view !is TextInputLayout) {
                return false
            }
            val error: CharSequence? = view.error
            return expectedErrorText == error
        }

        override fun describeTo(description: Description?) {}

    }
}
