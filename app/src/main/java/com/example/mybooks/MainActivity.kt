package com.example.mybooks

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.books.presentation.create.ui.CreateBookFragment
import com.example.books.presentation.detail.ui.BookDetailFragment
import com.example.books.presentation.detail.ui.ID_ARG
import com.example.books.presentation.list.ui.BookListFragment
import com.example.core.GoTo
import com.example.core.NavigationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navigationViewModel: NavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addBookListFragment(savedInstanceState)
        goTo()
    }

    private fun goTo() {
        navigationViewModel.goTo.observe(this) {
            when (it) {
                GoTo.Create -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<CreateBookFragment>(
                            R.id.fragment_container_view,
                            CreateBookFragment::class.java.name
                        )
                        addToBackStack(CreateBookFragment::class.java.name)
                    }
                }
                is GoTo.Detail -> {
                    val bundle = bundleOf(ID_ARG to it.id)
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<BookDetailFragment>(
                            R.id.fragment_container_view,
                            BookDetailFragment::class.java.name,
                            bundle
                        )
                        addToBackStack(BookDetailFragment::class.java.name)
                    }
                }
            }

        }
    }

    private fun addBookListFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<BookListFragment>(
                    R.id.fragment_container_view,
                    BookListFragment::class.java.name
                )
            }
        }
    }


}