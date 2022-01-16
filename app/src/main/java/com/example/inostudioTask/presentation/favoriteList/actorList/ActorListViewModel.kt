package com.example.inostudioTask.presentation.favoriteList.actorList

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inostudioTask.data.dataSource.dto.toActor
import com.example.inostudioTask.data.remote.dto.Actor
import com.example.inostudioTask.data.remote.dto.toActorEntity
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
        getActorList()
    }

    private fun getActorList() {
        _state.value = ListState.Loading
        val data = repository.actorListDatabase
        if (data.isNotEmpty()) {
            _state.value = ListState.Success(repository.actorListDatabase.map { it.toActor() })
        } else {
            _state.value = ListState.Empty
        }
    }

    private fun onDatabaseUpdate() {
        viewModelScope.launch {
            repository.updateDatabaseFlow.collect {
                getActorList()
            }
        }
    }

    fun deleteActor(actor: Actor) {
        viewModelScope.launch {
            repository.deleteActorDatabase(actor.toActorEntity())
        }
    }
}