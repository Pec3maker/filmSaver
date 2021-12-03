package com.example.inostudioTask.presentation.filmList

sealed class FilmListState<out T> {
    data class Success<T>(val data: List<T>): FilmListState<T>()
    object Loading : FilmListState<Nothing>()
    object Empty: FilmListState<Nothing>()
    data class Error(val exception: String?): FilmListState<Nothing>()
}
