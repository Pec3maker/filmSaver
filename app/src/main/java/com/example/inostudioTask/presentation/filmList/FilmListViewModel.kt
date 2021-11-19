package com.example.inostudioTask.presentation.filmList

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inostudioTask.R
import com.example.inostudioTask.common.Constants
import com.example.inostudioTask.common.Resource
import com.example.inostudioTask.domain.model.Film
import com.example.inostudioTask.domain.useCase.getFilms.GetFilmsUseCase
import com.example.inostudioTask.domain.useCase.getFilmsBySearch.GetFilmsBySearchUseCase
import com.example.inostudioTask.presentation.screenStates.ScreenStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FilmListViewModel @Inject constructor(
    private val getFilmsUseCase: GetFilmsUseCase,
    private val getFilmsBySearchUseCase: GetFilmsBySearchUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(ScreenStates.FilmListState<Any>())
    val state: State<ScreenStates.FilmListState<Any>> = _state


    init {
        refresh()
    }


    private fun getFilms(page: Int) {
        getFilmsUseCase(
            apiKey = Constants.API_KEY,
            page = page,
            language = Constants.LANGUAGE
        ).onEach { result->
            when(result) {
                is Resource.Success<*> -> {
                    @Suppress("UNCHECKED_CAST")
                    _state.value = ScreenStates.FilmListState(
                        data =  _state.value.data.plus(result.data as List<Film>)
                    )
                }
                is Resource.Error<*> -> {
                    _state.value = ScreenStates.FilmListState(
                        error = R.string.unexpected_error
                    )
                }
                is Resource.Loading<*> -> {
                    _state.value = ScreenStates.FilmListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getFilmsBySearch(page: Int, query: String) {
        getFilmsBySearchUseCase(
            apiKey = Constants.API_KEY,
            page = page,
            query = query,
            language = Constants.LANGUAGE
        ).onEach { result->
            when(result) {
                is Resource.Success<*> -> {
                    @Suppress("UNCHECKED_CAST")
                    _state.value = ScreenStates.FilmListState(
                        data =  _state.value.data.plus(result.data as List<Film>),
                        searchText = query
                    )
                }
                is Resource.Error<*> -> {
                    _state.value = ScreenStates.FilmListState(
                        error = R.string.unexpected_error
                    )
                }
                is Resource.Loading<*> -> {
                    _state.value = ScreenStates.FilmListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun refresh() {
        for(i in 1..10) {
            getFilms(page = i)
        }
    }

    fun searchFilms(query: String) {
        getFilmsBySearch(page = Constants.SEARCH_PAGES, query = query)
    }
}