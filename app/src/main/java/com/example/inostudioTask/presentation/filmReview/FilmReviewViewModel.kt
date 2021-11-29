package com.example.inostudioTask.presentation.filmReview

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inostudioTask.common.Constants
import com.example.inostudioTask.data.remote.dto.toFilm
import com.example.inostudioTask.domain.model.Film
import com.example.inostudioTask.domain.repository.FilmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
    lateinit var movieId: String

    init {
        savedStateHandle.get<String>("movie_id")?.let { id ->
            movieId = id
        }
        refresh()
    }

    private fun getFilm(id: String) {
        viewModelScope.launch {
            try {
                _state.value = FilmReviewState.Loading
                _state.value = FilmReviewState.Success(
                    data = repository.getFilmsById(
                        apiKey = Constants.api_key,
                        id = id,
                        language = Constants.language,
                        additionalInfo = Constants.additional_info
                    ).toFilm()
                )
            } catch (e: HttpException) {
                _state.value = FilmReviewState.Error(
                    exception = e
                )
            } catch (e: IOException) {
                _state.value = FilmReviewState.Error(
                    exception = e
                )
            }
        }
    }

    fun refresh() {
        getFilm(id = movieId)
    }
}