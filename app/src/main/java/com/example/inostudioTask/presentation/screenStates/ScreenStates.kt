package com.example.inostudioTask.presentation.screenStates

import com.example.inostudioTask.domain.model.dataBase.FilmEntity

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
        val searchText: String = "",
        var loadedFilm: FilmEntity? = null
    )
}

