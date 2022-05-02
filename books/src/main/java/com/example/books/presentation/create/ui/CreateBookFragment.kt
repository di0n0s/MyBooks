package com.example.books.presentation.create.ui

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.books.R
import com.example.books.data.entity.BookEntity
import com.example.books.databinding.FragmentCreateBookBinding
import com.example.books.presentation.create.viewModel.CreateBookState
import com.example.books.presentation.create.viewModel.CreateBookViewModel
import com.example.books.presentation.create.viewModel.UserIntent
import com.example.books.presentation.list.ui.CREATE_BOOK_ID_RESULT
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*


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
                    CreateBookState.Loading -> {
                        createButton?.isEnabled = false
                    }
                    is CreateBookState.Success -> {
                        setResultAndReturnToLastFragment(it)
                    }
                    is CreateBookState.Error -> {
                        createButton?.isEnabled = true
                        view?.let { view ->
                            it.error?.let { error ->
                                Snackbar.make(
                                    view,
                                    error,
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setResultAndReturnToLastFragment(it: CreateBookState.Success) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            CREATE_BOOK_ID_RESULT,
            it.bookId
        )
        findNavController().popBackStack()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
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
        priceTextInputEditText?.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter(2))
        priceTextInputLayout = binding?.priceInputLayout
        createButton = binding?.createButton
    }

    private fun setToolbar() {
        (activity as AppCompatActivity?)?.apply {
            setSupportActionBar(binding?.toolbar)
            if (supportActionBar != null) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black)
            }
        }
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
                isTitleEmpty()
            }
        }

        authorTextInputEditText?.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                isAuthorEmpty()
            }
        }

        priceTextInputEditText?.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                isPriceEmpty()
            }
        }
    }

    private fun onTextChangedListeners() {
        titleTextInputEditText?.addTextChangedListener {
            isTitleEmpty()
        }
        authorTextInputEditText?.addTextChangedListener {
            isAuthorEmpty()
        }
        priceTextInputEditText?.addTextChangedListener {
            isPriceEmpty()
        }
    }

    private fun setOnClickListeners() {
        createButton?.setOnClickListener {
            if (!isTitleEmpty() and !isAuthorEmpty() and !isPriceEmpty()) {
                hideKeyboard()
                lifecycleScope.launch {
                    viewModel.userIntent.send(
                        UserIntent.CreateBook(
                            BookEntity(
                                id = UUID.randomUUID().toString(),
                                title = titleTextInputEditText?.text.toString(),
                                author = authorTextInputEditText?.text.toString(),
                                price = priceTextInputEditText?.text.toString().toDouble()
                            )
                        )
                    )
                }
            }
        }
    }

    private fun hideKeyboard() {
        view?.let { view ->
            ViewCompat.getWindowInsetsController(view)
                ?.hide(WindowInsetsCompat.Type.ime())
        }
    }

    private fun isTitleEmpty(): Boolean =
        showErrorIfIsEmpty(titleTextInputLayout, titleTextInputEditText)

    private fun isAuthorEmpty(): Boolean =
        showErrorIfIsEmpty(authorTextInputLayout, authorTextInputEditText)

    private fun isPriceEmpty(): Boolean =
        showErrorIfIsEmpty(priceTextInputLayout, priceTextInputEditText)

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