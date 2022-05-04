package com.example.mybooks.tests

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.books.R
import com.example.books.di.BooksModule
import com.example.books.di.DataBaseModule
import com.example.books.presentation.create.ui.CreateBookFragment
import com.example.mybooks.launchFragmentInHiltContainer
import com.example.mybooks.utils.hasTextInputLayoutErrorText
import com.example.mybooks.utils.waitFor
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(DataBaseModule::class, BooksModule::class)
class CreateBookFragmentTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    fun given_noneEditTextFullFilled_when_buttonIsClicked_then_everyErrorIsVisible() {
        //GIVEN
        var string: String? = null

        //WHEN
        launchFragmentInHiltContainer<CreateBookFragment> {
            string = context?.getString(com.example.core.R.string.common_mandatory)
        }


        onView(withId(R.id.create_button)).perform(click())

        //THEN
        onView(withId(R.id.title_input_layout)).check(
            matches(
                string?.let {
                    hasTextInputLayoutErrorText(
                        it
                    )
                }
            )
        )

        onView(withId(R.id.author_input_layout)).check(
            matches(
                string?.let {
                    hasTextInputLayoutErrorText(
                        it
                    )
                }
            )
        )

        onView(withId(R.id.price_input_layout)).check(
            matches(
                string?.let {
                    hasTextInputLayoutErrorText(
                        it
                    )
                }
            )
        )

        onView(ViewMatchers.isRoot()).perform(waitFor(1000))
    }

    @Test
    fun given_titleIsNoFullFilled_when_changeTheFocus_then_titleErrorIsVisible() {
        //GIVEN
        var string: String? = null

        //WHEN
        launchFragmentInHiltContainer<CreateBookFragment> {
            string = context?.getString(com.example.core.R.string.common_mandatory)
        }


        onView(withId(R.id.title_input_edit_text)).perform(click())

        onView(withId(R.id.author_input_layout)).perform(click())

        //THEN
        onView(withId(R.id.title_input_layout)).check(
            matches(
                string?.let {
                    hasTextInputLayoutErrorText(
                        it
                    )
                }
            )
        )

        onView(ViewMatchers.isRoot()).perform(waitFor(1000))
    }

    @Test
    fun given_authorIsNoFullFilled_when_changeTheFocus_then_authorErrorIsVisible() {
        //GIVEN
        var string: String? = null

        //WHEN
        launchFragmentInHiltContainer<CreateBookFragment> {
            string = context?.getString(com.example.core.R.string.common_mandatory)
        }


        onView(withId(R.id.author_input_edit_text)).perform(click())

        onView(withId(R.id.title_input_edit_text)).perform(click())

        //THEN
        onView(withId(R.id.author_input_layout)).check(matches(string?.let {
            hasTextInputLayoutErrorText(
                it
            )
        }))

        onView(ViewMatchers.isRoot()).perform(waitFor(1000))
    }

    @Test
    fun given_priceIsNoFullFilled_when_changeTheFocus_then_priceErrorIsVisible() {
        //GIVEN
        var string: String? = null

        //WHEN
        launchFragmentInHiltContainer<CreateBookFragment> {
            string = context?.getString(com.example.core.R.string.common_mandatory)
        }


        onView(withId(R.id.price_input_edit_text)).perform(click())

        onView(withId(R.id.title_input_edit_text)).perform(click())

        //THEN
        onView(withId(R.id.price_input_layout)).check(matches(string?.let {
            hasTextInputLayoutErrorText(
                it
            )
        }))

        onView(ViewMatchers.isRoot()).perform(waitFor(1000))
    }

    @Test
    fun given_titleErrorIsVisible_when_typeText_then_titleErrorIsNotVisible() {
        //GIVEN
        var string: String? = null
        val title = "Alicia en el pais de las maravillas"


        //WHEN
        launchFragmentInHiltContainer<CreateBookFragment> {
            string = context?.getString(com.example.core.R.string.common_mandatory)
        }


        onView(withId(R.id.title_input_edit_text)).perform(click())

        onView(withId(R.id.author_input_layout)).perform(click())

        onView(withId(R.id.title_input_layout)).check(
            matches(
                string?.let {
                    hasTextInputLayoutErrorText(
                        it
                    )
                }
            )
        )

        onView(withId(R.id.title_input_edit_text)).perform(typeText(title))

        //THEN
        onView(withId(R.id.title_input_layout)).check(
            matches(hasTextInputLayoutErrorText(null))
        )

        onView(ViewMatchers.isRoot()).perform(waitFor(1000))
    }

    @Test
    fun given_authorErrorIsVisible_when_typeText_then_authorErrorIsNotVisible() {
        //GIVEN
        var string: String? = null
        val author = "Lewis Carrol"

        //WHEN
        launchFragmentInHiltContainer<CreateBookFragment> {
            string = context?.getString(com.example.core.R.string.common_mandatory)
        }


        onView(withId(R.id.author_input_edit_text)).perform(click())

        onView(withId(R.id.title_input_edit_text)).perform(click())

        onView(withId(R.id.author_input_layout)).check(
            matches(
                string?.let {
                    hasTextInputLayoutErrorText(
                        it
                    )
                }
            )
        )

        onView(withId(R.id.author_input_edit_text)).perform(typeText(author))

        //THEN
        onView(withId(R.id.author_input_layout)).check(
            matches(hasTextInputLayoutErrorText(null))
        )

        onView(ViewMatchers.isRoot()).perform(waitFor(1000))
    }

    @Test
    fun given_priceErrorIsVisible_when_typeText_then_priceErrorIsNotVisible() {
        //GIVEN
        var string: String? = null
        val price = "7.2"


        //WHEN
        launchFragmentInHiltContainer<CreateBookFragment> {
            string = context?.getString(com.example.core.R.string.common_mandatory)
        }


        onView(withId(R.id.price_input_edit_text)).perform(click())

        onView(withId(R.id.title_input_edit_text)).perform(click())

        onView(withId(R.id.price_input_layout)).check(
            matches(
                string?.let {
                    hasTextInputLayoutErrorText(
                        it
                    )
                }
            )
        )

        onView(withId(R.id.price_input_edit_text)).perform(typeText(price))

        //THEN
        onView(withId(R.id.price_input_layout)).check(
            matches(hasTextInputLayoutErrorText(null))
        )

        onView(ViewMatchers.isRoot()).perform(waitFor(1000))
    }
}