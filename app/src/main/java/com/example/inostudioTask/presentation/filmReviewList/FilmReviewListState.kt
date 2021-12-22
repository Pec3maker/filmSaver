package com.example.inostudioTask.presentation.filmReviewList

sealed class FilmReviewListState<out T> {
    object Loading: FilmReviewListState<Nothing>()
    data class Success<T>(val data: List<T>): FilmReviewListState<T>()
    data class Error<T>(val message: String): FilmReviewListState<T>()
}