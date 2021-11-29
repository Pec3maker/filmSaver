package com.example.inostudioTask.presentation.filmList.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
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
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(filmList) { film ->
            film.isInDatabase = isContainFilm(film)
            FilmListItem(
                film = film,
                onItemClick = { navigate(it.id) },
                onFavoriteClick = {
                    if (film.isInDatabase!!) {
                        deleteFilm(it)
                    } else {
                        saveFilm(it)
                    }
                },
                textButton =
                if (film.isInDatabase!!) {
                    context.getString(R.string.delete_favorite)
                } else {
                    context.getString(R.string.add_favorite)
                }
            )
        }
    }
}
