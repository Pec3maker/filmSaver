package com.example.inostudioTask.presentation.screenStates

sealed class ScreenStates
{

    data class FilmReviewState<T>(
        val isLoading: Boolean = false,
        val data: T? = null,
        val error: T? = null,
    )

    data class FilmListState<T>(
        val isLoading: Boolean = false,
        val data: List<T> = emptyList(),
        val error: T? = null,
        val searchText: String = ""
    )
}

