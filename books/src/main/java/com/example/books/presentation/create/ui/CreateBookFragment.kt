package com.example.books.presentation.create.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.books.R
import com.example.books.databinding.FragmentCreateBookBinding
import com.example.books.presentation.create.viewModel.CreateBookState
import com.example.books.presentation.create.viewModel.CreateBookViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateBookFragment : Fragment() {

    //Views
    private var _binding: FragmentCreateBookBinding? = null
    private val binding get() = _binding

    private var titleTextInputLayout: TextInputLayout? = null
    private var titleTextInputEditText: TextInputEditText? = null

    private var authorTextInputLayout: TextInputLayout? = null
    private var authorTextInputEditText: TextInputEditText? = null

    private var priceTextInputLayout: TextInputLayout? = null
    private var priceTextInputEditText: TextInputEditText? = null

    private var createButton: MaterialButton? = null

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
        onFocusChangedListeners()
        onTextChangedListeners()
        setOnClickListeners()
        return binding?.root
    }

    private fun onInitView(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentCreateBookBinding.inflate(inflater, container, false)
        titleTextInputEditText = binding?.titleInputEditText
        titleTextInputLayout = binding?.titleInputLayout
        authorTextInputEditText = binding?.authorInputEditText
        authorTextInputLayout = binding?.authorInputLayout
        priceTextInputEditText = binding?.priceInputEditText
        priceTextInputLayout = binding?.priceInputLayout
        createButton = binding?.createButton
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

    private fun showErrorIfIsEmpty(
        textInputLayout: TextInputLayout?,
        textInputEditText: TextInputEditText?
    ): Boolean {
        val isEmpty = textInputEditText?.text.isNullOrBlank()
        if (isEmpty) {
            textInputLayout?.error = getString(com.example.core.R.string.common_mandatory)
        } else {
            textInputLayout?.error = null
        }
        return isEmpty
    }

    private fun onFocusChangedListeners() {
        titleTextInputEditText?.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                checkTitle()
            }
        }

        authorTextInputEditText?.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                checkAuthor()
            }
        }

        priceTextInputEditText?.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                checkPrice()
            }
        }
    }

    private fun onTextChangedListeners() {
        titleTextInputEditText?.addTextChangedListener {
            checkTitle()
        }
        authorTextInputEditText?.addTextChangedListener {
            checkAuthor()
        }
        priceTextInputEditText?.addTextChangedListener {
            checkPrice()
        }
    }

    private fun setOnClickListeners() {
        createButton?.setOnClickListener {
            if (checkTitle() && checkAuthor() && checkPrice()) {
                //TODO save book
            }
        }
    }

    private fun checkTitle(): Boolean =
        showErrorIfIsEmpty(titleTextInputLayout, titleTextInputEditText)

    private fun checkAuthor(): Boolean =
        showErrorIfIsEmpty(authorTextInputLayout, authorTextInputEditText)

    private fun checkPrice(): Boolean =
        showErrorIfIsEmpty(priceTextInputLayout, priceTextInputEditText)

}