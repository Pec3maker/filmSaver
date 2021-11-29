package com.example.inostudioTask.presentation.filmList

import java.lang.Exception

sealed class FilmListState<out T> {
    data class Success<T>(val data: List<T>): FilmListState<T>()
    object Loading : FilmListState<Nothing>()
    object Empty: FilmListState<Nothing>()
    data class Error<T>(val exception: Exception?): FilmListState<T>()
}
