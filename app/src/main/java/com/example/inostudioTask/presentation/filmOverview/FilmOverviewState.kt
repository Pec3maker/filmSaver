package com.example.inostudioTask.presentation.filmOverview

sealed class FilmOverviewState<out T> {
    data class Success<T>(val data: T): FilmOverviewState<T>()
    object Loading : FilmOverviewState<Nothing>()
    data class Error(val message: String?): FilmOverviewState<Nothing>()
}