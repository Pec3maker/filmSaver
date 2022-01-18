package com.example.inostudioTask.presentation.castList

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inostudioTask.common.Constants
import com.example.inostudioTask.data.remote.dto.Actor
import com.example.inostudioTask.data.remote.dto.toActorEntity
import com.example.inostudioTask.domain.repository.FilmRepository
import com.example.inostudioTask.presentation.common.ListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CastListViewModel @Inject constructor(
    private val repository: FilmRepository
): ViewModel() {

    private val _state = mutableStateOf<ListState<Actor>>(ListState.Loading)
    val state: State<ListState<Actor>> = _state

    init {
        onDatabaseUpdate()
        refresh()
    }

    fun refresh() {
        getActorsList(page = Constants.SEARCH_PAGE)
    }

    fun addFavorite(actor: Actor) {
        if (actor.isInDatabase!!) {
            deleteActor(actor = actor)
        } else {
            saveActor(actor = actor)
        }
    }

    private fun onDatabaseUpdate() {
        viewModelScope.launch {
            repository.actorListFlow.collect {
                val actorListState = _state.value
                if (actorListState is ListState.Success) {
                    _state.value = fillActorAccessory(actorListState.data)
                }
            }
        }
    }

    private fun saveActor(actor: Actor) {
        viewModelScope.launch {
            repository.insertActorDatabase(actor.toActorEntity())
        }
    }

    private fun deleteActor(actor: Actor) {
        viewModelScope.launch {
            repository.deleteActorDatabase(actor.toActorEntity())
        }
    }

    private fun getActorsList(page: Int) {
        viewModelScope.launch {
            try {
                _state.value = ListState.Loading
                val data = repository.getActorsList(
                    apiKey = Constants.API_KEY,
                    page = page,
                    language = Constants.LANGUAGE
                )
                _state.value = fillActorAccessory(data)
            } catch (e: HttpException) {
                _state.value = ListState.Error(message = e.message?: "")
            } catch (e: IOException) {
                _state.value = ListState.Error(message = e.message?: "")
            }
        }
    }

    private fun fillActorAccessory(actorList: List<Actor>): ListState.Success<Actor>{
        val changedFilmList = actorList.toMutableList().apply {
            replaceAll { actor ->
                actor.copy(isInDatabase = repository.actorListFlow.value.any { it.id == actor.id })
            }
        }
        return ListState.Success(changedFilmList)
    }
}