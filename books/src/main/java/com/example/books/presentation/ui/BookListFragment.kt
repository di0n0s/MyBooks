package com.example.books.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.books.R
import com.example.books.databinding.FragmentBookListBinding
import com.example.books.presentation.adapter.BookListAdapter
import com.example.books.presentation.viewModel.BookListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookListFragment : Fragment() {

    //Views
    private var _binding: FragmentBookListBinding? = null
    private val binding get() = _binding

    //Adapter
    private var adapter: BookListAdapter? = null

    //ViewModel
    private val viewModel: BookListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onInitView(inflater, container)
        setToolbar()
        setObserver()
        return binding?.root
    }

    private fun onInitView(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentBookListBinding.inflate(inflater, container, false)
        initAdapter()
    }

    private fun initAdapter() {
        adapter = BookListAdapter()
    }

    private fun setToolbar() {
        (activity as AppCompatActivity?)?.apply {
            setSupportActionBar(binding?.toolbar)
            supportActionBar?.title = getString(R.string.book_list_book_list)
        }
    }

    private fun setObserver() {
        lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest { pagingData ->
                adapter?.submitData(pagingData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        _binding = null
    }
}