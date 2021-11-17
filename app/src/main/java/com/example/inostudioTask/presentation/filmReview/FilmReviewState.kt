package com.example.inostudioTask.presentation.filmReview

import com.example.inostudioTask.domain.model.Film

data class FilmReviewState(
    val isLoading: Boolean = false,
    val film: Film? = null,
    val error: Int? = null
)
