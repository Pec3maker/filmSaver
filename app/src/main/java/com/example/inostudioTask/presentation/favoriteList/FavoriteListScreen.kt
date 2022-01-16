package com.example.inostudioTask.presentation.favoriteList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.inostudioTask.R
import com.example.inostudioTask.presentation.favoriteList.actorList.ActorListScreen
import com.example.inostudioTask.presentation.favoriteList.filmList.FilmListScreen

@Composable
fun FavoriteListScreen(
    navController: NavController,
    viewModel: FavoriteListViewModel = hiltViewModel()
) {

    val items = listOf(
        Tabs.FilmsScreen,
        Tabs.ActorsScreen
    )

    Box(Modifier.fillMaxSize()) {
        Column {
            TabRow(
                selectedTabIndex = viewModel.tabRowState.value,
                backgroundColor = MaterialTheme.colors.background
            ) {
                items.forEachIndexed { index, tab ->
                    Tab(
                        selected = viewModel.tabRowState.value == index,
                        onClick = { viewModel.tabRowState.value = index },
                        icon = {
                            Icon(imageVector = tab.icon, contentDescription = null)
                        },
                        text = {
                            Text(text = stringResource(id = tab.text))
                        }
                    )
                }
            }

            if (viewModel.tabRowState.value == items.indexOf(Tabs.FilmsScreen)) {
                FilmListScreen(navController = navController)
            } else {
                ActorListScreen(navController = navController)
            }
        }
    }
}

sealed class Tabs(val text: Int, val icon: ImageVector) {
    object FilmsScreen: Tabs(text = R.string.films, icon = Icons.Filled.Movie)
    object ActorsScreen: Tabs(text = R.string.actors, icon = Icons.Filled.Person)
}