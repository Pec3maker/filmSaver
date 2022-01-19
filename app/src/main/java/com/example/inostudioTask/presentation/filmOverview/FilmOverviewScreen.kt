package com.example.inostudioTask.presentation.filmOverview

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
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
    val uiState = viewModel.state.value

    Box(modifier = Modifier.fillMaxSize()) {
        when(uiState) {
            is ReviewState.Success -> {
                FilmOverviewSuccessScreen(
                    film = uiState.data,
                    onFavoriteClick = { viewModel.addFavorite(it) },
                    onReviewClick = {
                        navController.navigate(
                            "${Screens.FilmReviewListScreen.route}/${uiState.data.id}"
                        )
                    },
                    onActorClick = {
                        navController.navigate(
                            "${Screens.ActorReviewScreen.route}/$it"
                        ) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            is ReviewState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is ReviewState.Error -> {
                ErrorScreen(
                    text = uiState.message?: "",
                    onButtonClick = { viewModel.refresh() }
                )
            }
        }
    }
}