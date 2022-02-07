package com.example.inostudioTask.presentation.filmOverview

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inostudioTask.common.Constants
import com.example.inostudioTask.data.remote.dto.Film
import com.example.inostudioTask.domain.repository.FilmRepository
import com.example.inostudioTask.presentation.common.ReviewState
import com.example.inostudioTask.presentation.common.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FilmOverviewViewModel @Inject constructor(
    private val repository: FilmRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf<ReviewState<Film>>(ReviewState.Loading)
    val state: State<ReviewState<Film>> = _state
    private val movieId: String = savedStateHandle
        .get<String>(Screens.FilmReviewScreen.NAV_ARGUMENT_NAME)!!

    init {
        onDatabaseUpdate()
        refresh()
    }

    fun refresh() {
        getFilm()
    }

    fun onFavoriteClick(film: Film) {
        viewModelScope.launch {
            repository.onFavoriteClick(film = film)
        }
    }

    private fun getFilm() {
        viewModelScope.launch {
            try {
                _state.value = ReviewState.Loading
                val film = repository.getFilmsById(
                    apiKey = Constants.API_KEY,
                    id = movieId,
                    language = Constants.LANGUAGE,
                    additionalInfo = "videos,images,reviews,credits"
                )
                _state.value = fillFilmAccessory(film)
            } catch (e: HttpException) {
                _state.value = ReviewState.Error(
                    message = e.message
                )
            } catch (e: IOException) {
                _state.value = ReviewState.Error(
                    message = e.message
                )
            }
        }
    }

    private fun onDatabaseUpdate() {
        viewModelScope.launch {
            repository.filmListFlow.collect {
                val filmListState = _state.value
                if (filmListState is ReviewState.Success) {
                    _state.value = fillFilmAccessory(filmListState.data)
                }
            }
        }
    }

    private fun fillFilmAccessory(film: Film): ReviewState.Success<Film> {
        val changedFilm = film.copy(
            isInDatabase = repository.filmListFlow.value.any { it.id == film.id }
        )
        return ReviewState.Success(changedFilm)
    }
}