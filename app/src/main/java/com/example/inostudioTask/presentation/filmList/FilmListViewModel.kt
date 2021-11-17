package com.example.inostudioTask.presentation.filmList

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inostudioTask.R
import com.example.inostudioTask.common.Constants
import com.example.inostudioTask.common.Resource
import com.example.inostudioTask.domain.useCase.getFilms.GetFilmsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FilmListViewModel @Inject constructor(
    private val getFilmsUseCase: GetFilmsUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(FilmListState())
    val state: State<FilmListState> = _state

    init {
        for(i in 1..10) {
            getFilms(apiKey = Constants.API_KEY, page = i, language = Constants.LANGUAGE)
        }
    }



    private fun getFilms(apiKey: String, page: Int, language: String) {
        getFilmsUseCase(apiKey = apiKey, page = page, language = language).onEach { result->
            when(result) {
                is Resource.Success -> {
                    _state.value = FilmListState(
                        films = result.data?.plus(_state.value.films) ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _state.value = FilmListState(
                        error = result.message ?:  R.string.unexpected_error
                    )
                }
                is Resource.Loading -> {
                    _state.value = FilmListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}