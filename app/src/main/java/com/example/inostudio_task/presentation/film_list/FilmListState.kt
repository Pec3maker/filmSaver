package com.example.inostudio_task.presentation.film_list

import com.example.inostudio_task.domain.model.Film

data class FilmListState(
    val isLoading: Boolean = false,
    val films: List<Film> = emptyList(),
    val error: String = "",
)
