package com.example.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NavigationViewModel : ViewModel() {

    private val _goTo = MutableLiveData<GoTo>()
    val goTo: LiveData<GoTo>
        get() = _goTo

    fun goToBookDetail(id: String) {
        _goTo.value = GoTo.Detail(id)
    }

    fun goToCreateBook() {
        _goTo.value = GoTo.Create
    }
}

sealed class GoTo {
    data class Detail(val id: String) : GoTo()
    object Create : GoTo()
}