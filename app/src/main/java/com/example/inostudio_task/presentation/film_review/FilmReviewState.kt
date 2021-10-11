package com.example.inostudio_task.presentation.film_review

import com.example.inostudio_task.data.remote.dto.FilmDetailDto
import com.example.inostudio_task.domain.model.Film

data class FilmReviewState(
    val isLoading: Boolean = false,
    val film: Film? = null,
    val error: String = ""
)
