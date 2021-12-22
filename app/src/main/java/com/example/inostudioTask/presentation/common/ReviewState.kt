package com.example.inostudioTask.presentation.common

sealed class ReviewState<out T> {
    data class Success<T>(val data: T): ReviewState<T>()
    object Loading : ReviewState<Nothing>()
    data class Error(val message: String?): ReviewState<Nothing>()
}