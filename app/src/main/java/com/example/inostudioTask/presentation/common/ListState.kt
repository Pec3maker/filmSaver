package com.example.inostudioTask.presentation.common

sealed class ListState<out T> {
    object Loading : ListState<Nothing>()
    data class Success<T>(val data: List<T>) : ListState<T>()
    data class Error(val message: String) : ListState<Nothing>()
    object Empty : ListState<Nothing>()
}