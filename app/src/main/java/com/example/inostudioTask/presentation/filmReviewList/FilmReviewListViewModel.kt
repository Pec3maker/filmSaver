package com.example.inostudioTask.presentation.filmReviewList

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inostudioTask.common.Constants
import com.example.inostudioTask.data.remote.dto.ReviewResponse
import com.example.inostudioTask.domain.repository.FilmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FilmReviewListViewModel @Inject constructor(
    private val repository: FilmRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private lateinit var movieId: String
    private val _state = mutableStateOf<FilmReviewListState<ReviewResponse>>(FilmReviewListState.Loading)
    val state: State<FilmReviewListState<ReviewResponse>> = _state

    init {
        savedStateHandle.get<String>("movie_id")?.let {
            movieId = it
        }
        refresh()
    }

    fun refresh() {
        searchReviews(page = Constants.SEARCH_PAGE)
    }

    private fun searchReviews(page: Int) {
        viewModelScope.launch {
            try {
                _state.value = FilmReviewListState.Loading
                val data = repository.getReviewList(
                    apiKey = Constants.API_KEY,
                    id = movieId,
                    page = page,
                    language = Constants.LANGUAGE
                )
                _state.value = FilmReviewListState.Success(data)
            } catch (e: HttpException) {
                _state.value = FilmReviewListState.Error(e.message?: "")
            } catch (e: IOException) {
                _state.value = FilmReviewListState.Error(e.message?: "")
            }
        }
    }
}