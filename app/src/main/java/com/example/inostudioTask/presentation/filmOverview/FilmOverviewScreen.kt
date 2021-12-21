package com.example.inostudioTask.presentation.filmOverview

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.inostudioTask.presentation.common.Screen
import com.example.inostudioTask.presentation.common.ErrorScreen
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

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when(uiState) {
            is FilmOverviewState.Success -> {
                FilmOverviewSuccessScreen(
                    film = uiState.data,
                    onFavoriteClick = { viewModel.addFavorite(it) },
                    navigate = { navController.navigate(Screen.FilmReviewListScreen.route) }
                )
            }
            is FilmOverviewState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is FilmOverviewState.Error -> {
                ErrorScreen(
                    text = uiState.message?: "",
                    onButtonClick = { viewModel.refresh() }
                )
            }
        }
    }
}