package com.example.inostudioTask.presentation.filmList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.inostudioTask.R
import com.example.inostudioTask.data.remote.dto.Film
import com.example.inostudioTask.presentation.Screen
import com.example.inostudioTask.presentation.common.ErrorScreen
import com.example.inostudioTask.presentation.filmList.components.*

@Composable
fun FilmListScreen(
    navController: NavController,
    viewModel: FilmListViewModel = hiltViewModel()
) {
    val uiState = viewModel.state.value
    val context = LocalContext.current

    Column {
        SearchBar(
            hint = context.getString(R.string.searchbar_hint),
            modifier = Modifier
                .fillMaxWidth(),
            text = viewModel.searchText.value,
            onTextChange = { viewModel.searchFilms(it) }
        )

        when (uiState) {
            is FilmListState.Success -> {
                FilmListSuccessScreen(
                    filmList = uiState.data,
                    navigate = {
                        navController.navigate("${Screen.FilmReviewScreen.route}/${it}")
                    },
                    addFavorite = { viewModel.addFavorite(it) },
                )
            }
            is FilmListState.Loading -> {
                FilmListLoadingScreen()
            }
            is FilmListState.Error -> {
                ErrorScreen(
                    onButtonClick = { viewModel.refresh() },
                    text = uiState.exception?: ""
                )
            }
            is FilmListState.Empty -> {
                FilmListEmptyScreen()
            }
        }
    }
}

@Composable
fun FilmListEmptyScreen() {
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = context.getString(R.string.not_found),
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