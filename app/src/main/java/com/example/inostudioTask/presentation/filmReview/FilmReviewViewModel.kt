package com.example.inostudioTask.presentation.filmReview

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inostudioTask.common.Constants
import com.example.inostudioTask.data.remote.dto.Film
import com.example.inostudioTask.data.remote.dto.toFilmEntity
import com.example.inostudioTask.domain.model.dataBase.FilmEntity
import com.example.inostudioTask.domain.repository.FilmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
    private var filmListDatabase = emptyList<FilmEntity>()

    init {
        savedStateHandle.get<String>("movie_id")?.let {
            movieId = it
        }
        getFilmsDatabase()
        refresh()
    }

    private fun getFilm(id: String) {
        viewModelScope.launch {
            try {
                _state.value = FilmReviewState.Loading
                val film = repository.getFilmsById(
                    apiKey = Constants.API_KEY,
                    id = id,
                    language = Constants.LANGUAGE,
                    additionalInfo = Constants.ADDITIONAL_INFO
                )
                _state.value = fillFilmAccessory(film)
            } catch (e: HttpException) {
                _state.value = FilmReviewState.Error(
                    exception = e.message
                )
            } catch (e: IOException) {
                _state.value = FilmReviewState.Error(
                    exception = e.message
                )
            }
        }
    }

    fun addFavorite(film: Film) {
        if (film.isInDatabase!!) {
            deleteFilm(film = film)
        } else {
            saveFilm(film = film)
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

    private fun getFilmsDatabase() {
        repository.getFilmsDatabase().onEach { films ->
            filmListDatabase = films
            val currentState = _state.value
            if (currentState is FilmReviewState.Success) {
                _state.value = fillFilmAccessory(currentState.data)
            }
        }.launchIn(viewModelScope)
    }

    private fun fillFilmAccessory(film: Film): FilmReviewState.Success<Film>{
        val changedFilm = film.copy(isInDatabase = filmListDatabase.any { it.id == film.id })
        return FilmReviewState.Success(changedFilm)
    }

    fun refresh() {
        getFilm(id = movieId)
    }
}