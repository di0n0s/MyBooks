package com.example.books.presentation

interface NavigationListener {
    fun goToBookDetail(id: String)
    fun goToCreateBook()
}