package com.example.inostudioTask.presentation.filmReview

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inostudioTask.R
import com.example.inostudioTask.common.Constants
import com.example.inostudioTask.common.Resource
import com.example.inostudioTask.domain.repository.FilmRepository
import com.example.inostudioTask.presentation.screenStates.ScreenStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FilmReviewViewModel @Inject constructor(

    private val repository: FilmRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = mutableStateOf(ScreenStates.FilmReviewState<Any>())
    val state: State<ScreenStates.FilmReviewState<Any>> = _state

    init {
        savedStateHandle.get<String>("movie_id")?.let { movieId ->
            refresh(movieId)
        }
    }

    private fun getFilm(apiKey: String, id: String, language: String) {
        repository.getFilmsByIdUseCase(
            apiKey = apiKey,
            id = id,
            language = language
        ).onEach { result ->
            when(result) {
                is Resource.Success<*> -> {
                    _state.value = ScreenStates.FilmReviewState(data = result.data)
                }
                is Resource.Error<*> -> {
                    _state.value = ScreenStates.FilmReviewState(
                        error = R.string.unexpected_error
                    )
                }
                is Resource.Loading<*> -> {
                    _state.value = ScreenStates.FilmReviewState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun refresh(movieId: String) {
        getFilm(apiKey = Constants.API_KEY, id = movieId, Constants.LANGUAGE)
    }
}