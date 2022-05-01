package com.example.books.presentation.list.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.books.R
import com.example.books.databinding.FragmentBookListBinding
import com.example.books.presentation.list.adapter.BookListAdapter
import com.example.books.presentation.list.adapter.PaginationListener
import com.example.books.presentation.list.viewModel.BookListViewModel
import com.example.books.presentation.list.viewModel.GetPagedBookListState
import com.example.books.presentation.list.viewModel.UserIntent
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

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
        adapter = BookListAdapter(viewModel.bookListVo)
        recyclerView?.adapter = adapter
        recyclerView?.addOnScrollListener(object :
            PaginationListener(recyclerView?.layoutManager as LinearLayoutManager, PAGE_SIZE) {
            override fun loadMoreItems() {
                isLoading = true
                lifecycleScope.launch {
                    viewModel.userIntent.send(UserIntent.GetPagedBookList(PAGE_SIZE))
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
        if (viewModel.bookListVo.isEmpty()) {
            lifecycleScope.launch {
                viewModel.userIntent.send(UserIntent.GetPagedBookList(PAGE_SIZE))

            }
        }
    }

    private fun setOnClickListener() {
        fab?.setOnClickListener {
            val request = NavDeepLinkRequest.Builder
                .fromUri(getString(com.example.core.R.string.create_book_fragment_uri).toUri())
                .build()
            findNavController().navigate(request)
        }
    }

    private fun onNavigationResult() {
        val savedStateHandle = findNavController().currentBackStackEntry?.savedStateHandle

        savedStateHandle?.getLiveData<UUID>(
            CREATE_BOOK_ID_RESULT
        )?.observe(viewLifecycleOwner) { result ->
            lifecycleScope.launch {
                viewModel.userIntent.send(UserIntent.GetLastBookCreated(result))
            }
            savedStateHandle.remove<UUID>(CREATE_BOOK_ID_RESULT)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        _binding = null
    }
}