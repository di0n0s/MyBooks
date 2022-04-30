package com.example.books.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.books.R
import com.example.books.databinding.FragmentBookListBinding
import com.example.books.presentation.adapter.BookListAdapter
import com.example.books.presentation.adapter.PaginationListener
import com.example.books.presentation.viewModel.BookListViewModel
import com.example.books.presentation.viewModel.GetPagedBookListState
import com.example.books.presentation.viewModel.UserIntent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookListFragment : Fragment() {

    //Views
    private var _binding: FragmentBookListBinding? = null
    private val binding get() = _binding

    private var recyclerView: RecyclerView? = null

    //Variables
    private var adapter: BookListAdapter? = null
    private var isLoading = false
    private var isLastPage = false

    //ViewModel
    private val viewModel: BookListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectState()
    }

    private fun collectState() {
        lifecycleScope.launch {
            viewModel.bookListState.collect {
                when (it) {
                    GetPagedBookListState.Idle -> {}
                    GetPagedBookListState.Loading -> {}
                    is GetPagedBookListState.Success -> {
                        adapter?.removeLoading()
                        adapter?.addItems(it.list)
                        if (it.list.isEmpty()) {
                            isLastPage = true
                        } else {
                            adapter?.addLoading()
                        }
                        isLoading = false
                    }
                    is GetPagedBookListState.Error -> {}
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onInitView(inflater, container)
        setToolbar()
        getFirstPage()
        return binding?.root
    }

    private fun onInitView(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentBookListBinding.inflate(inflater, container, false)
        recyclerView = binding?.recyclerView
        initAdapter()
    }

    private fun initAdapter() {
        adapter = BookListAdapter(arrayListOf())
        recyclerView?.adapter = adapter
        recyclerView?.addOnScrollListener(object :
            PaginationListener(recyclerView?.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                val pageSize = this.pageSize
                lifecycleScope.launch {
                    val start = adapter?.itemCount
                    viewModel.userIntent.send(UserIntent.GetPagedBookList(start, pageSize))
                }
            }

            override fun isLastPage(): Boolean = isLastPage

            override fun isLoading(): Boolean = isLoading

        })
    }

    private fun setToolbar() {
        (activity as AppCompatActivity?)?.apply {
            setSupportActionBar(binding?.toolbar)
            supportActionBar?.title = getString(R.string.book_list_book_list)
        }
    }

    private fun getFirstPage() {
        lifecycleScope.launch {
            viewModel.userIntent.send(UserIntent.GetPagedBookList(1, 10))

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        _binding = null
    }
}