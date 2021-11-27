package com.example.inostudioTask.presentation.filmList

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.inostudioTask.R
import com.example.inostudioTask.domain.model.Film
import com.example.inostudioTask.presentation.Screen
import com.example.inostudioTask.presentation.filmList.components.FilmListErrorScreen
import com.example.inostudioTask.presentation.filmList.components.FilmListLoadingScreen
import com.example.inostudioTask.presentation.filmList.components.FilmListSuccessScreen
import com.example.inostudioTask.presentation.filmList.components.SearchBar

@Composable
@Suppress("UNCHECKED_CAST")
fun FilmListScreen(
    navController: NavController,
    viewModel: FilmListViewModel = hiltViewModel()
) {
    val uiState = viewModel.state.value
    val databaseFilms = remember { viewModel.filmListDatabase }
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
                    filmList = uiState.data as List<Film>,
                    databaseFilms = databaseFilms.value,
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
                    errorText = context.getString(uiState.data as Int)
                )
            }
        }
    }
}