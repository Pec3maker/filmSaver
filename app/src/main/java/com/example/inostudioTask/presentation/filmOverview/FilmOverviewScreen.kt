package com.example.inostudioTask.presentation.filmOverview

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.inostudioTask.presentation.common.ReviewState
import com.example.inostudioTask.presentation.common.Screens
import com.example.inostudioTask.presentation.common.components.ErrorScreen
import com.example.inostudioTask.presentation.filmOverview.components.FilmOverviewSuccessScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun FilmOverviewScreen(
    viewModel: FilmOverviewViewModel = hiltViewModel(),
    navController: NavController
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (val uiState = viewModel.state.value) {
            is ReviewState.Success -> {
                FilmOverviewSuccessScreen(
                    film = uiState.data,
                    onFavoriteClick = { viewModel.onFavoriteClick(it) },
                    onReviewClick = {
                        navController.navigate(
                            Screens.FilmReviewListScreen.getNavigationRoute(uiState.data.id)
                        )
                    },
                    onActorClick = {
                        navController.navigate(Screens.ActorReviewScreen.getNavigationRoute(it))
                    }
                )
            }
            is ReviewState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is ReviewState.Error -> {
                ErrorScreen(
                    text = uiState.message ?: "",
                    onButtonClick = { viewModel.refresh() }
                )
            }
        }
    }
}