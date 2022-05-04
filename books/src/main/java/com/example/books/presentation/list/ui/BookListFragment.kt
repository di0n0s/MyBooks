package com.example.books.presentation.list.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.books.R
import com.example.books.databinding.FragmentBookListBinding
import com.example.books.presentation.NavigationListener
import com.example.books.presentation.list.adapter.BookListAdapter
import com.example.books.presentation.list.adapter.PaginationListener
import com.example.books.presentation.list.viewModel.BookListViewModel
import com.example.books.presentation.list.viewModel.GetPagedBookListState
import com.example.books.presentation.list.viewModel.UserIntent
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

const val CREATE_BOOK_ID_REQUEST = "CREATE_BOOK_ID_REQUEST"
const val CREATE_BOOK_ID_RESULT = "CREATE_BOOK_ID_RESULT"

@AndroidEntryPoint
class BookListFragment : Fragment() {

    //Views
    private var _binding: FragmentBookListBinding? = null
    private val binding get() = _binding

    private var recyclerView: RecyclerView? = null
    private var fab: FloatingActionButton? = null

    //Variables
    private var adapter: BookListAdapter? = null
    private var isLoading = false
    private var isLastPage = false
    private var navigation: NavigationListener? = null

    //ViewModel
    private val viewModel: BookListViewModel by viewModels()

    companion object {
        private const val PAGE_SIZE = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectState()
    }

    private fun collectState() {
        lifecycleScope.launch {
            viewModel.bookListState.collect {
                when (it) {
                    GetPagedBookListState.Idle -> {}
                    GetPagedBookListState.Loading -> {
                        isLoading = true
                    }
                    is GetPagedBookListState.Success -> {
                        adapter?.removeLoading()
                        if (it.list.isEmpty()) {
                            isLastPage = true
                        } else {
                            adapter?.addItems(it.list)
                            if (!isLastPage) {
                                adapter?.addLoading()
                            }
                        }
                        isLoading = false
                    }
                    is GetPagedBookListState.Error -> {
                        isLoading = false
                    }
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
        setOnClickListener()
        onNavigationResult()
        return binding?.root
    }

    private fun onInitView(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentBookListBinding.inflate(inflater, container, false)
        recyclerView = binding?.recyclerView
        fab = binding?.fab
        initAdapter()
    }

    private fun initAdapter() {
        adapter = BookListAdapter(viewModel.bookListVo) {
            goToDetail(it)
        }
        recyclerView?.adapter = adapter
        setSpanSize()
        recyclerView?.addOnScrollListener(object :
            PaginationListener(recyclerView?.layoutManager as GridLayoutManager, PAGE_SIZE) {
            override fun loadMoreItems() {
                lifecycleScope.launch {
                    viewModel.userIntent.send(UserIntent.GetPagedBookList(PAGE_SIZE))
                }
            }

            override fun isLastPage(): Boolean = isLastPage

            override fun isLoading(): Boolean = isLoading

        })
    }

    private fun goToDetail(it: String) {
        navigation?.goToBookDetail(it)
    }

    private fun setSpanSize() {
        (recyclerView?.layoutManager as GridLayoutManager).spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {

                    return when (adapter?.getItemViewType(position)) {
                        BookListAdapter.VIEW_TYPE_NORMAL -> 1
                        BookListAdapter.VIEW_TYPE_LOADING -> 3
                        else -> -1
                    }
                }

            }
    }

    private fun setToolbar() {
        (activity as AppCompatActivity?)?.apply {
            setSupportActionBar(binding?.toolbar)
            supportActionBar?.title = getString(R.string.book_list_book_list)
        }
    }

    private fun getFirstPage() {
        if (viewModel.bookListVo.isEmpty()) {
            lifecycleScope.launch {
                viewModel.userIntent.send(UserIntent.GetPagedBookList(PAGE_SIZE))
            }
        }
    }

    private fun setOnClickListener() {
        fab?.setOnClickListener {
            navigation?.goToCreateBook()
        }
    }

    private fun onNavigationResult() {
        setFragmentResultListener(CREATE_BOOK_ID_REQUEST) { _, bundle ->
            val id = bundle.getString(CREATE_BOOK_ID_RESULT)
            if (id != null) {
                lifecycleScope.launch {
                    viewModel.userIntent.send(UserIntent.GetLastBookCreated(id))
                }
            }
        }
    }

    fun setNavigationListener(listener: NavigationListener) {
        navigation = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        _binding = null
    }
}