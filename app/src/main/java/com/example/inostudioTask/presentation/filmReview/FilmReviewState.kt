package com.example.inostudioTask.presentation.filmReview

import java.lang.Exception

sealed class FilmReviewState<T> {
    data class Success<T>(val data: T): FilmReviewState<T>()
    object Loading : FilmReviewState<Nothing>()
    data class Error<T>(val data: T, val exception: Exception?): FilmReviewState<T>()
}
