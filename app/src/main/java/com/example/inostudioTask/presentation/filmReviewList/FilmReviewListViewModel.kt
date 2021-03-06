package com.example.inostudioTask.presentation.filmReviewList

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inostudioTask.common.FilmRepository
import com.example.inostudioTask.data.remote.dto.ReviewResponse
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

    private val movieId: String = savedStateHandle
        .get<String>(Screens.FilmReviewListScreen.NAV_ARGUMENT_NAME)!!
    private val _state = mutableStateOf<ListState<ReviewResponse>>(ListState.Loading)
    val state: State<ListState<ReviewResponse>> = _state

    init {
        refresh()
    }

    fun refresh() {
        searchReviews()
    }

    private fun searchReviews() {
        viewModelScope.launch {
            try {
                _state.value = ListState.Loading
                val data = repository.getReviewList(id = movieId)
                _state.value = ListState.Success(data)
            } catch (e: HttpException) {
                _state.value = ListState.Error(e.message ?: "")
            } catch (e: IOException) {
                _state.value = ListState.Error(e.message ?: "")
            }
        }
    }
}