package com.example.books.presentation.list.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.books.R
import com.example.books.databinding.ItemBookBinding
import com.example.books.presentation.list.vo.BookPaginationVo

class BookViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.item_book, parent, false
    )
) {

    private val binding = ItemBookBinding.bind(itemView)

    fun bind(book: BookPaginationVo.BookVo) {
        with(binding) {
            titleTextView.text = book.title
            Glide.with(itemView)
                .load(book.imageUrl)
                .placeholder(R.drawable.ic_book_placeholder_2)
                .into(imageView)
        }
    }
}