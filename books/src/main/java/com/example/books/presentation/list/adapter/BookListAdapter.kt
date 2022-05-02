package com.example.books.presentation.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.books.presentation.list.ui.BookViewHolder
import com.example.books.presentation.list.ui.ProgressViewHolder
import com.example.books.presentation.list.vo.BookPaginationVo


class BookListAdapter(private val list: ArrayList<BookPaginationVo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLoaderVisible = false

    companion object {
        const val VIEW_TYPE_LOADING = 0
        const val VIEW_TYPE_NORMAL = 1
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

    fun addItems(items: List<BookPaginationVo>) {
        val positionStart = list.size + 1
        list.addAll(items)
        notifyItemRangeInserted(positionStart, items.size)
    }


    fun addLoading() {
        isLoaderVisible = true
        list.add(BookPaginationVo.Loading)
        notifyItemInserted(list.size - 1)
    }

    fun removeLoading() {
        isLoaderVisible = false
        val wasRemoved = list.removeAll { it is BookPaginationVo.Loading }
        if (wasRemoved) {
            notifyItemRemoved(list.size - 1)
        }
    }


}