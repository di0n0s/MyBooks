package com.example.mybooks.tests

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.books.R
import com.example.books.di.BooksModule
import com.example.books.di.DataBaseModule
import com.example.books.presentation.list.ui.BookViewHolder
import com.example.mybooks.launchMainActivity
import com.example.mybooks.utils.ScrollToBottomAction
import com.example.mybooks.utils.waitFor
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@UninstallModules(DataBaseModule::class, BooksModule::class)
@HiltAndroidTest
class BookListFragmentTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    fun when_fragmentIsLaunched_then_recyclerViewIsVisible() {
        //GIVEN


        //WHEN
        launchMainActivity()

        //THEN
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }


    @Test
    fun when_bookIsCreated_then_isDisplayedOnList() {
        //GIVEN
        val title = "Alicia en el pais de las maravillas"
        val author = "Lewis Carrol"
        val price = "7.2"

        //WHEN

        launchMainActivity()

        onView(withId(R.id.fab)).perform(click())

        onView(withId(R.id.title_input_edit_text)).perform(typeText(title))

        onView(withId(R.id.author_input_edit_text)).perform(typeText(author))

        onView(withId(R.id.price_input_edit_text)).perform(typeText(price))

        onView(withId(R.id.create_button)).perform(click())

        onView(withId(R.id.recycler_view)).perform(ScrollToBottomAction())

        onView(isRoot()).perform(waitFor(1000))

        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<BookViewHolder>(
                20,
                click()
            )
        )

        //THEN

        onView(withId(R.id.title_text_view)).check(matches(withText(title)))

        onView(withId(R.id.author_text_view)).check(matches(withText(author)))

        onView(withId(R.id.price_text_view)).check(matches(withText("$price €")))

        onView(isRoot()).perform(waitFor(1000))
    }

    @Test
    fun when_pressBackOnCreateScreen_then_showListAgain() {
        //GIVEN


        //WHEN

        launchMainActivity()

        onView(withId(R.id.fab)).perform(click())

        pressBack()

        //THEN

        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))

        onView(isRoot()).perform(waitFor(1000))
    }

    @Test
    fun when_pressBackOnDetailScreen_then_showListAgain() {
        //GIVEN


        //WHEN

        launchMainActivity()

        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<BookViewHolder>(
                0,
                click()
            )
        )

        pressBack()

        //THEN

        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))

        onView(isRoot()).perform(waitFor(1000))
    }
}