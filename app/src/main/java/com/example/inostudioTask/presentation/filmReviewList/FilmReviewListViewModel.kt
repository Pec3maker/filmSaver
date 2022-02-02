package com.example.inostudioTask.presentation.filmReviewList

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inostudioTask.common.Constants
import com.example.inostudioTask.data.remote.dto.ReviewResponse
import com.example.inostudioTask.domain.repository.FilmRepository
import com.example.inostudioTask.presentation.common.ListState
import com.example.inostudioTask.presentation.common.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FilmReviewListViewModel @Inject constructor(
    private val repository: FilmRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private lateinit var movieId: String
    private val _state = mutableStateOf<ListState<ReviewResponse>>(ListState.Loading)
    val state: State<ListState<ReviewResponse>> = _state

    init {
        savedStateHandle.get<String>(Screens.FilmReviewListScreen.NAV_ARGUMENT_NAME)?.let {
            movieId = it
        }
        refresh()
    }

    fun refresh() {
        searchReviews()
    }

    private fun searchReviews() {
        viewModelScope.launch {
            try {
                _state.value = ListState.Loading
                val data = repository.getReviewList(
                    apiKey = Constants.API_KEY,
                    id = movieId,
                    page = Constants.SEARCH_PAGE,
                    language = Constants.LANGUAGE
                )
                _state.value = ListState.Success(data)
            } catch (e: HttpException) {
                _state.value = ListState.Error(e.message ?: "")
            } catch (e: IOException) {
                _state.value = ListState.Error(e.message ?: "")
            }
        }
    }
}