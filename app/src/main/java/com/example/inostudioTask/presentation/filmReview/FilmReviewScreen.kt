package com.example.inostudioTask.presentation.filmReview

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.inostudioTask.presentation.filmReview.components.FilmReviewErrorScreen
import com.example.inostudioTask.presentation.filmReview.components.FilmReviewSuccessScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun FilmReviewScreen(
    viewModel: FilmReviewViewModel = hiltViewModel(),
) {
    val uiState = viewModel.state.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        when(uiState) {
            is FilmReviewState.Success -> {
                FilmReviewSuccessScreen(film = uiState.data)
            }
            is FilmReviewState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is FilmReviewState.Error -> {
                FilmReviewErrorScreen(
                    text = uiState.exception?.message?: "",
                    onButtonClick = { viewModel.refresh() }
                )
            }
        }
    }
}