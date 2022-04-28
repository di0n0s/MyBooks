package com.example.books.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.books.R
import com.example.books.data.entity.BookEntity
import com.example.books.databinding.ItemBookBinding

class BookViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.item_book, parent, false
    )
) {

    private val binding = ItemBookBinding.bind(itemView)

    fun bind(book: BookEntity?) {
        with(binding) {
            numberTextView.text = book?.id.toString()
            titleTextView.text = book?.title
        }
    }
}