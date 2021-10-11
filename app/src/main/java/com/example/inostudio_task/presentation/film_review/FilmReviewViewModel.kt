package com.example.inostudio_task.presentation.film_review

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inostudio_task.common.Constants
import com.example.inostudio_task.common.Resource
import com.example.inostudio_task.domain.use_case.get_film.GetFilmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FilmReviewViewModel @Inject constructor(
    private val getFilmUseCase: GetFilmUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(FilmReviewState())
    val state: State<FilmReviewState> = _state

    init {
        savedStateHandle.get<String>("movie_id")?.let { filmId ->
            getFilm(Constants.API_KEY, filmId, "ru")
        }
    }

    private fun getFilm(apiKey: String, id: String, language: String) {
        getFilmUseCase(apiKey = apiKey, id = id, language = language).onEach { result->
            when(result) {
                is Resource.Success -> {
                    _state.value = FilmReviewState(film = result.data)
                }
                is Resource.Error -> {
                    _state.value = FilmReviewState(
                        error = result.message ?:  "Unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = FilmReviewState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}