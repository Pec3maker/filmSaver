package com.example.inostudioTask.presentation.filmReview

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inostudioTask.R
import com.example.inostudioTask.common.Constants
import com.example.inostudioTask.common.Resource
import com.example.inostudioTask.domain.useCase.getFilm.GetFilmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FilmReviewViewModel @Inject constructor(

    private val getFilmUseCase: GetFilmUseCase,
    @ApplicationContext private val context: Context,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = mutableStateOf(FilmReviewState())
    val state: State<FilmReviewState> = _state

    init {
        savedStateHandle.get<String>("movie_id")?.let { filmId ->
            getFilm(Constants.API_KEY, filmId, Constants.LANGUAGE)
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
                        error = result.message ?: context.getString(R.string.unexpected_error)
                    )
                }
                is Resource.Loading -> {
                    _state.value = FilmReviewState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}