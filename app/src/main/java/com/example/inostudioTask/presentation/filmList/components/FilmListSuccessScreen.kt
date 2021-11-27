package com.example.inostudioTask.presentation.filmList.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.inostudioTask.R
import com.example.inostudioTask.domain.model.Film

@Composable
fun FilmListSuccessScreen(
    filmList: List<Film>,
    navigate: (Int) -> Unit,
    deleteFilm: (Film) -> Unit,
    saveFilm: (Film) -> Unit,
    isContainFilm: (Film) -> Boolean
) {
    val context = LocalContext.current
    if (filmList.isEmpty()) {
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
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(filmList) { film ->
                var text by remember { mutableStateOf("") }
                text = if (isContainFilm(film)) {
                    context.getString(R.string.delete_favorite)
                } else {
                    context.getString(R.string.add_favorite)
                }

                FilmListItem(
                    film = film,
                    onItemClick = { navigate(it.id) },
                    onFavoriteClick = {
                        text = if (isContainFilm(film)) {
                            deleteFilm(it)
                            context.getString(R.string.add_favorite)
                        } else {
                            saveFilm(it)
                            context.getString(R.string.delete_favorite)
                        }
                    },
                    textButton = text
                )
            }
        }
    }
}