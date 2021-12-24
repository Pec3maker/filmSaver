package com.example.inostudioTask.presentation.actorReview

import androidx.lifecycle.ViewModel
import com.example.inostudioTask.domain.repository.FilmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActorReviewViewModel @Inject constructor(
    private val repository: FilmRepository
): ViewModel() {
    init {

    }
}