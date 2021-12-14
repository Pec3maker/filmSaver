package com.example.inostudioTask.presentation.filmReview

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inostudioTask.common.Constants
import com.example.inostudioTask.data.remote.dto.Film
import com.example.inostudioTask.data.remote.dto.toFilmEntity
import com.example.inostudioTask.domain.repository.FilmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FilmReviewViewModel @Inject constructor(
    private val repository: FilmRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = mutableStateOf<FilmReviewState<Film>>(FilmReviewState.Loading)
    val state: State<FilmReviewState<Film>> = _state
    private lateinit var movieId: String

    init {
        savedStateHandle.get<String>("movie_id")?.let {
            movieId = it
        }
        onDatabaseUpdate()
        refresh()
    }

    fun refresh() {
        getFilm()
    }

    fun addFavorite(film: Film) {
        if (film.isInDatabase!!) {
            deleteFilm(film = film)
        } else {
            saveFilm(film = film)
        }
    }

    private fun getFilm() {
        viewModelScope.launch {
            try {
                _state.value = FilmReviewState.Loading
                val film = repository.getFilmsById(
                    apiKey = Constants.API_KEY,
                    id = movieId,
                    language = Constants.LANGUAGE,
                    additionalInfo = Constants.ADDITIONAL_INFO
                )
                _state.value = fillFilmAccessory(film)
            } catch (e: HttpException) {
                _state.value = FilmReviewState.Error(
                    message = e.message
                )
            } catch (e: IOException) {
                _state.value = FilmReviewState.Error(
                    message = e.message
                )
            }
        }
    }

    private fun onDatabaseUpdate() {
        viewModelScope.launch {
            repository.updateDatabaseFlow.collect {
                val filmListState = _state.value
                if (filmListState is FilmReviewState.Success) {
                    _state.value = fillFilmAccessory(filmListState.data)
                }
            }
        }
    }

    private fun saveFilm(film: Film) {
        viewModelScope.launch {
            repository.insertFilmDatabase(film.toFilmEntity())
        }
    }

    private fun deleteFilm(film: Film) {
        viewModelScope.launch {
            repository.deleteFilmDatabase(film.toFilmEntity())
        }
    }

    private fun fillFilmAccessory(film: Film): FilmReviewState.Success<Film>{
        val changedFilm = film.copy(
            isInDatabase = repository.filmListDatabase.any { it.id == film.id }
        )
        return FilmReviewState.Success(changedFilm)
    }
}