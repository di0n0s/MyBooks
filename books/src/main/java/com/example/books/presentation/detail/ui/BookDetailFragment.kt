package com.example.books.presentation.detail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.books.R
import com.example.books.databinding.FragmentBookDetailBinding
import com.example.books.presentation.detail.viewModel.BookDetailViewModel
import com.example.books.presentation.detail.viewModel.GetBookState
import com.example.books.presentation.detail.viewModel.UserIntent
import com.example.books.presentation.detail.vo.BookDetailVo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

const val ID_ARG = "ID_ARG"

@AndroidEntryPoint
class BookDetailFragment : Fragment() {

    //Views
    private var _binding: FragmentBookDetailBinding? = null
    private val binding get() = _binding

    private var titleTextView: TextView? = null
    private var imageView: ImageView? = null
    private var authorTextView: TextView? = null
    private var priceTextView: TextView? = null


    //ViewModel
    private val viewModel: BookDetailViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectState()
    }

    private fun collectState() {
        lifecycleScope.launch {
            viewModel.bookState.collect {
                when (it) {
                    GetBookState.Idle -> {}
                    GetBookState.Loading -> {
                        //Use Skeleton content loader
                    }
                    is GetBookState.Success -> {
                        renderUi(it.book)
                    }
                    is GetBookState.Error -> {
                        //Show error
                    }
                }
            }
        }
    }

    private fun renderUi(book: BookDetailVo) {
        imageView?.let { imageView ->
            Glide.with(this)
                .load(book.imageUrl)
                .placeholder(R.drawable.ic_book_placeholder_2)
                .into(imageView)
        }
        titleTextView?.text = book.title
        authorTextView?.text = book.author
        priceTextView?.text = book.price
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        onInitView(inflater, container)
        setToolbar()
        getBook()
        return binding?.root
    }

    private fun onInitView(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentBookDetailBinding.inflate(inflater, container, false)
        titleTextView = binding?.titleTextView
        imageView = binding?.imageView
        authorTextView = binding?.authorTextView
        priceTextView = binding?.priceTextView
    }

    private fun setToolbar() {
        (activity as AppCompatActivity?)?.apply {
            setSupportActionBar(binding?.toolbar)
            if (supportActionBar != null) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            }
        }
    }

    private fun getBook() {
        lifecycleScope.launch {
            val string = arguments?.getString(ID_ARG)
            string?.let {
                viewModel.userIntent.send(UserIntent.GetBook(it))
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController().popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}