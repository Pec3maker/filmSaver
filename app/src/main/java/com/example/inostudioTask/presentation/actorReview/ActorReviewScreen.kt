package com.example.inostudioTask.presentation.actorReview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.inostudioTask.presentation.actorReview.components.ActorReviewSuccessScreen
import com.example.inostudioTask.presentation.common.ReviewState
import com.example.inostudioTask.presentation.common.Screens
import com.example.inostudioTask.presentation.common.components.ErrorScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@Composable
fun ActorReviewScreen(
    viewModel: ActorReviewViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState = viewModel.state.value

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {

            is ReviewState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is ReviewState.Success -> {
                ActorReviewSuccessScreen(
                    actor = uiState.data,
                    onFavoriteClick = { viewModel.onFavoriteClick(it) },
                    onFilmClick = {
                        navController.navigate(
                            Screens.FilmReviewScreen.getNavigationRoute(it)
                        )
                    }
                )
            }

            is ReviewState.Error -> {
                ErrorScreen(
                    onButtonClick = { viewModel.refresh() },
                    text = uiState.message?: ""
                )
            }
        }
    }
}