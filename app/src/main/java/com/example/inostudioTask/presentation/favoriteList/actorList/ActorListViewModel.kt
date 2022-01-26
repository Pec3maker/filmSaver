package com.example.inostudioTask.presentation.favoriteList.actorList

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inostudioTask.data.dataSource.dto.toActor
import com.example.inostudioTask.data.remote.dto.Actor
import com.example.inostudioTask.domain.repository.FilmRepository
import com.example.inostudioTask.presentation.common.ListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorListViewModel @Inject constructor(
    private val repository: FilmRepository
) : ViewModel() {

    private val _state = mutableStateOf<ListState<Actor>>(ListState.Loading)
    val state: State<ListState<Actor>> = _state

    init {
        onDatabaseUpdate()
    }

    private fun onDatabaseUpdate() {
        viewModelScope.launch {
            repository.actorListFlow.collect {
                if (it.isNotEmpty()) {
                    _state.value = ListState.Success(
                        it.map { actorEntity -> actorEntity.toActor() }
                    )
                } else {
                    _state.value = ListState.Empty
                }
            }
        }
    }

    fun deleteActor(actor: Actor) {
        repository.addFavoriteActor(actor = actor.copy(isInDatabase = true))
    }
}