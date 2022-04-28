package com.example.books.presentation.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.books.data.entity.BookEntity
import com.example.books.presentation.ui.BookViewHolder

class BookListAdapter : PagingDataAdapter<BookEntity, BookViewHolder>(differCallback) {

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<BookEntity>() {
            override fun areItemsTheSame(oldItem: BookEntity, newItem: BookEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: BookEntity,
                newItem: BookEntity
            ): Boolean = oldItem == newItem

        }
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) =
        holder.bind(getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder =
        BookViewHolder(parent)
}