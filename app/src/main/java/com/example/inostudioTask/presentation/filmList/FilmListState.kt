package com.example.inostudioTask.presentation.filmList

import java.lang.Exception

sealed class FilmListState<T> {
    data class Success<T>(val data: T): FilmListState<T>()
    object Loading : FilmListState<Nothing>()
    data class Error<T>(val data: T, val exception: Exception?): FilmListState<T>()
}
