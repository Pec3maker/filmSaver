package com.example.inostudioTask.presentation.favoriteList.filmList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.inostudioTask.presentation.common.ListState
import com.example.inostudioTask.presentation.common.Screen
import com.example.inostudioTask.presentation.common.components.FilmListComponent
import com.example.inostudioTask.presentation.favoriteList.components.EmptyScreen

@Composable
fun FilmListScreen(
    viewModel: FilmListViewModel = hiltViewModel(),
    navController: NavController
) {
    Box(Modifier.fillMaxSize()) {
        when (val uiState = viewModel.state.value) {

            is ListState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is ListState.Empty -> {
                EmptyScreen()
            }

            is ListState.Success -> {
                FilmListComponent(
                    filmList = uiState.data,
                    navigate = {
                        navController.navigate("${Screen.FilmReviewScreen.route}/$it")
                    },
                    addFavorite = { viewModel.deleteFilm(it) }
                )
            }

            is ListState.Error -> Unit
        }
    }
}