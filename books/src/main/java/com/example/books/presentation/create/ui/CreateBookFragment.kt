package com.example.books.presentation.create.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.books.R
import com.example.books.databinding.FragmentCreateBookBinding
import com.example.books.presentation.create.viewModel.CreateBookState
import com.example.books.presentation.create.viewModel.CreateBookViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateBookFragment : Fragment() {

    //Views
    private var _binding: FragmentCreateBookBinding? = null
    private val binding get() = _binding

    //ViewModel
    private val viewModel: CreateBookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectState()
    }

    private fun collectState() {
        lifecycleScope.launch {
            viewModel.createBookState.collect {
                when (it) {
                    CreateBookState.Idle -> {}
                    CreateBookState.Loading -> {}
                    is CreateBookState.Success -> {
                        //TODO notify list fragment call getBook
                    }
                    is CreateBookState.Error -> {}
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
        return binding?.root
    }

    private fun onInitView(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentCreateBookBinding.inflate(inflater, container, false)
    }

    private fun setToolbar() {
        (activity as AppCompatActivity?)?.apply {
            setSupportActionBar(binding?.toolbar)
            supportActionBar?.title = getString(R.string.create_book_create_book)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}