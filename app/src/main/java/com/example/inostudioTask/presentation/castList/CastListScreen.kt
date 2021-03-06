package com.example.inostudioTask.presentation.castList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.inostudioTask.presentation.common.ListState
import com.example.inostudioTask.presentation.common.Screens
import com.example.inostudioTask.presentation.common.components.ActorListComponent
import com.example.inostudioTask.presentation.common.components.ErrorScreen

@Composable
fun CastListScreen(
    viewModel: CastListViewModel = hiltViewModel(),
    navController: NavController
) {
    Box(Modifier.fillMaxSize()) {
        when (val uiState = viewModel.state.value) {
            is ListState.Error -> {
                ErrorScreen(
                    onButtonClick = { viewModel.refresh() },
                    text = uiState.message
                )
            }
            is ListState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is ListState.Success -> {
                ActorListComponent(
                    actorList = uiState.data,
                    navigate = {
                        navController.navigate(
                            Screens.ActorReviewScreen.getNavigationRoute(it)
                        )
                    },
                    addFavorite = { viewModel.onFavoriteClick(it) }
                )
            }
            is ListState.Empty -> Unit
        }
    }
}