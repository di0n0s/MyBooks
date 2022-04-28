package com.example.books.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.books.presentation.BookPaginationVo
import com.example.books.presentation.ui.BookViewHolder
import com.example.books.presentation.ui.ProgressViewHolder


class BookListAdapter(private val list: ArrayList<BookPaginationVo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLoaderVisible = false

    companion object {
        private const val VIEW_TYPE_LOADING = 0
        private const val VIEW_TYPE_NORMAL = 1
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BookViewHolder) {
            holder.bind(list[position] as BookPaginationVo.BookVo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_NORMAL -> BookViewHolder(parent)
            VIEW_TYPE_LOADING -> ProgressViewHolder(parent)
            else -> BookViewHolder(parent)
        }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] is BookPaginationVo.BookVo) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_LOADING
        }
    }

    override fun getItemCount(): Int = list.size


    fun addLoading() {
        isLoaderVisible = true
        list.add(BookPaginationVo.Loading)
        notifyItemInserted(list.size - 1)
    }

    fun removeLoading() {
        isLoaderVisible = false
        list.removeAll { it is BookPaginationVo.Loading }
        notifyItemRemoved(list.size - 1)
    }

}