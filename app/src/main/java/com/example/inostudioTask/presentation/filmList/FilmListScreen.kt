package com.example.inostudioTask.presentation.filmList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.inostudioTask.R
import com.example.inostudioTask.data.remote.dto.Film
import com.example.inostudioTask.presentation.Screen
import com.example.inostudioTask.presentation.common.ErrorScreen
import com.example.inostudioTask.presentation.filmList.components.*
import kotlinx.coroutines.flow.collect

@Composable
fun FilmListScreen(
    navController: NavController,
    viewModel: FilmListViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
) {
    val uiState = viewModel.uiState.value
    val searchText = viewModel.searchTextState.value
    
    LaunchedEffect(scaffoldState.snackbarHostState) {
        viewModel.errorMessage.collect {
            scaffoldState.snackbarHostState.showSnackbar(
                message = it
            )
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

        when (uiState) {
            is FilmListState.Success -> {
                FilmListSuccessScreen(
                    filmList = uiState.data,
                    navigate = {
                        navController.navigate("${Screen.FilmReviewScreen.route}/${it}")
                    },
                    addFavorite = { viewModel.addFavorite(it) },
                    progressBarState = viewModel.progressBarState.value
                )
            }
            is FilmListState.Error -> {
                ErrorScreen(
                    onButtonClick = { viewModel.searchFilms() },
                    text = uiState.message?: ""
                )
            }
            is FilmListState.Empty -> {
                FilmListEmptyScreen(searchText)
            }
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

@Composable
fun FilmListSuccessScreen(
    filmList: List<Film>,
    navigate: (Int) -> Unit,
    addFavorite: (Film) -> Unit,
    progressBarState: Boolean
) {
    if (progressBarState) {
        FilmListLoadingScreen()
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(top = 5.dp)
            .padding(horizontal = 11.dp),
        shape = MaterialTheme.shapes.small,
        elevation = 5.dp
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(filmList) { film ->
                FilmListItem(
                    film = film,
                    onItemClick = { navigate(it.id) },
                    onFavoriteClick = { addFavorite(it) }
                )
            }
        }
    }
}