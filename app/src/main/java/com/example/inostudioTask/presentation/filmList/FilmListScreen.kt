package com.example.inostudioTask.presentation.filmList

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.inostudioTask.R
import com.example.inostudioTask.presentation.common.ListState
import com.example.inostudioTask.presentation.common.Screens
import com.example.inostudioTask.presentation.common.components.ErrorScreen
import com.example.inostudioTask.presentation.common.components.FilmListComponent
import com.example.inostudioTask.presentation.filmList.components.*
import kotlinx.coroutines.flow.collect

@Composable
fun FilmListScreen(
    navController: NavController,
    viewModel: FilmListViewModel = hiltViewModel(),
    showSnackBar: suspend (String) -> Unit
) {
    val uiState = viewModel.uiState.value
    val searchText = viewModel.searchTextState.value

    LaunchedEffect(true) {
        viewModel.errorMessage.collect {
            showSnackBar(it)
        }
    }

    Column {
        SearchBar(
            hint = stringResource(id = R.string.searchbar_hint),
            modifier = Modifier
                .fillMaxWidth(),
            text = searchText,
            onTextChange = {
                viewModel.onSearchTextUpdate(it)
            }
        )

        if (viewModel.progressBarState.value) {
            FilmListLoadingScreen()
        }

        when (uiState) {
            is ListState.Success -> {
                FilmListComponent(
                    filmList = uiState.data,
                    navigate = {
                        navController.navigate(
                            "${Screens.FILM_REVIEW.route}/${it}"
                        )
                    },
                    addFavorite = { viewModel.addFavorite(it) }
                )
            }
            is ListState.Error -> {
                ErrorScreen(
                    onButtonClick = { viewModel.searchFilms() },
                    text = uiState.message
                )
            }
            is ListState.Empty -> {
                FilmListEmptyScreen(searchText = searchText)
            }
            is ListState.Loading -> Unit
        }
    }
}

@Composable
fun FilmListEmptyScreen(
    searchText: String
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = stringResource(R.string.not_found, searchText),
            color = MaterialTheme.colors.error
        )
    }
}

@Composable
fun FilmListLoadingScreen() {
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 11.dp)
    )
    Spacer(modifier = Modifier.padding(5.dp))
}