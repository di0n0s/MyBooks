package com.example.mybooks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.books.presentation.NavigationListener
import com.example.books.presentation.create.ui.CreateBookFragment
import com.example.books.presentation.detail.ui.BookDetailFragment
import com.example.books.presentation.detail.ui.ID_ARG
import com.example.books.presentation.list.ui.BookListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addBookListFragment(savedInstanceState)
        addFragmentOnAttachListener()
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

    override fun goToBookDetail(id: String) {
        val bundle = bundleOf(ID_ARG to id)
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

    override fun goToCreateBook() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<CreateBookFragment>(
                R.id.fragment_container_view,
                CreateBookFragment::class.java.name
            )
            addToBackStack(CreateBookFragment::class.java.name)
        }
    }

    private fun addFragmentOnAttachListener() {
        supportFragmentManager.addFragmentOnAttachListener { _, fragment ->
            if (fragment is BookListFragment) {
                fragment.setNavigationListener(this)
            }
        }
    }


}