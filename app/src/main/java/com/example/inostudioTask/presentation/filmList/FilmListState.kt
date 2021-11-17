package com.example.inostudioTask.presentation.filmList

import com.example.inostudioTask.domain.model.Film

data class FilmListState(
    val isLoading: Boolean = false,
    val films: List<Film> = emptyList(),
    val error: Int? = null
)
