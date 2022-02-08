package com.example.inostudioTask.presentation.actorReview

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inostudioTask.common.FilmRepository
import com.example.inostudioTask.data.remote.dto.Actor
import com.example.inostudioTask.presentation.common.ReviewState
import com.example.inostudioTask.presentation.common.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ActorReviewViewModel @Inject constructor(
    private val repository: FilmRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val personId: String = savedStateHandle
        .get<String>(Screens.ActorReviewScreen.NAV_ARGUMENT_NAME)!!
    private val _state = mutableStateOf<ReviewState<Actor>>(ReviewState.Loading)
    val state: State<ReviewState<Actor>> = _state

    init {
        onDatabaseUpdate()
        refresh()
    }

    fun refresh() {
        getActor()
    }

    fun onFavoriteClick(actor: Actor) {
        viewModelScope.launch {
            repository.onFavoriteClick(actor = actor)
        }
    }

    private fun getActor() {
        viewModelScope.launch {
            try {
                _state.value = ReviewState.Loading
                val data = repository.getActorDetails(personId = personId)
                _state.value = fillActorAccessory(actor = data)
            } catch (e: HttpException) {
                _state.value = ReviewState.Error(message = e.message)
            } catch (e: IOException) {
                _state.value = ReviewState.Error(message = e.message)
            }
        }
    }

    private fun onDatabaseUpdate() {
        viewModelScope.launch {
            repository.actorListFlow.collect {
                val actorListState = _state.value
                if (actorListState is ReviewState.Success) {
                    _state.value = fillActorAccessory(actorListState.data)
                }
            }
        }
    }

    private fun fillActorAccessory(actor: Actor): ReviewState.Success<Actor> {
        val changedActor = actor.copy(
            isInDatabase = repository.actorListFlow.value.any { it.id == actor.id }
        )
        return ReviewState.Success(changedActor)
    }
}