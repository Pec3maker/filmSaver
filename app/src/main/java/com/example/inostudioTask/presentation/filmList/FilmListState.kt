package com.example.inostudioTask.presentation.filmList

sealed class FilmListState<out T> {
    data class Success<T>(val data: List<T>): FilmListState<T>()
    object Empty: FilmListState<Nothing>()
    data class Error(val message: String?): FilmListState<Nothing>()
}