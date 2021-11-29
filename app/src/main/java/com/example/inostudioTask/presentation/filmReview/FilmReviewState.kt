package com.example.inostudioTask.presentation.filmReview

import java.lang.Exception

sealed class FilmReviewState<out T> {
    data class Success<T>(val data: T): FilmReviewState<T>()
    object Loading : FilmReviewState<Nothing>()
    data class Error<T>(val exception: Exception?): FilmReviewState<T>()
}
