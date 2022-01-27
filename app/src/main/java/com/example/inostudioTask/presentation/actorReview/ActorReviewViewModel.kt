package com.example.inostudioTask.presentation.actorReview

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inostudioTask.common.Constants
import com.example.inostudioTask.data.remote.dto.Actor
import com.example.inostudioTask.domain.repository.FilmRepository
import com.example.inostudioTask.presentation.common.ReviewState
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
): ViewModel() {

    private lateinit var personId: String
    private val _state = mutableStateOf<ReviewState<Actor>>(ReviewState.Loading)
    val state: State<ReviewState<Actor>> = _state

    init {
        savedStateHandle.get<String>("actor_id")?.let {
            personId = it
        }
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
                val data = repository.getActorDetails(
                    apiKey = Constants.API_KEY,
                    language = Constants.LANGUAGE,
                    personId = personId,
                    additionalInfo = Constants.ACTOR_ADDITIONAL_INFO
                )
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