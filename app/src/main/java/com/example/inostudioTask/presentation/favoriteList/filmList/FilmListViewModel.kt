package com.example.inostudioTask.presentation.favoriteList.filmList

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inostudioTask.data.dataSource.dto.toFilm
import com.example.inostudioTask.data.remote.dto.Film
import com.example.inostudioTask.domain.repository.FilmRepository
import com.example.inostudioTask.presentation.common.ListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmListViewModel @Inject constructor(
    private val repository: FilmRepository
): ViewModel() {

    private val _state = mutableStateOf<ListState<Film>>(ListState.Loading)
    val state: State<ListState<Film>> = _state

    init {
        onDatabaseUpdate()
    }

    private fun onDatabaseUpdate() {
        viewModelScope.launch {
            repository.filmListFlow.collect {
                if (it.isNotEmpty()) {
                    _state.value = ListState.Success(
                        it.map { filmEntity -> filmEntity.toFilm() }
                    )
                } else {
                    _state.value = ListState.Empty
                }
            }
        }
    }

    fun deleteFilm(film: Film) {
        repository.addFavoriteFilm(film.copy(isInDatabase = true))
    }
}