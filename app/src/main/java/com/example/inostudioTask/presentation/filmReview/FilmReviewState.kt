package com.example.inostudioTask.presentation.filmReview

sealed class FilmReviewState<out T> {
    data class Success<T>(val data: T): FilmReviewState<T>()
    object Loading : FilmReviewState<Nothing>()
    data class Error(val exception: String?): FilmReviewState<Nothing>()
}
