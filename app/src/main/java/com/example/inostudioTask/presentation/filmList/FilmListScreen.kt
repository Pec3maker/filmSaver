package com.example.inostudioTask.presentation.filmList

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.inostudioTask.R
import com.example.inostudioTask.presentation.Screen
import com.example.inostudioTask.presentation.filmList.components.*

@Composable
fun FilmListScreen(
    navController: NavController,
    viewModel: FilmListViewModel = hiltViewModel()
) {
    val uiState = viewModel.state.value
    val context = LocalContext.current
    val searchText = remember { viewModel.searchText }

    Column {
        SearchBar(
            hint = context.getString(R.string.searchbar_hint),
            modifier = Modifier
                .fillMaxWidth(),
            text = searchText.value,
            onTextChange = {
                searchText.value = it
                viewModel.searchFilms()
            }
        )

        when (uiState) {
            is FilmListState.Success -> {
                FilmListSuccessScreen(
                    filmList = uiState.data,
                    navigate = {
                        navController.navigate("${Screen.FilmReviewScreen.route}/${it}")
                    },
                    deleteFilm = { viewModel.deleteFilm(it) },
                    saveFilm = { viewModel.saveFilm(it) },
                    isContainFilm = { viewModel.isContainFilm(it) }
                )
            }
            is FilmListState.Loading -> {
                FilmListLoadingScreen()
            }
            is FilmListState.Error -> {
                FilmListErrorScreen(
                    onClickButton = { viewModel.refresh() },
                    errorText = uiState.exception?.message ?: ""
                )
            }
            is FilmListState.Empty -> {
                FilmListEmptyScreen()
            }
        }
    }
}