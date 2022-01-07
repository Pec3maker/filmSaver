package com.example.inostudioTask.presentation.actorReview.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.inostudioTask.R
import com.example.inostudioTask.data.remote.dto.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager

@ExperimentalPagerApi
@Composable
fun SuccessScreen(
    actor: Actor,
    onFavoriteClick: (Actor) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Poster(actor)

        Spacer(modifier = Modifier.padding(2.dp))

        ActorInfo(actor)

        Spacer(modifier = Modifier.padding(2.dp))

        Pager(actor)

        Spacer(modifier = Modifier.padding(2.dp))

        Movies(actor)

        Spacer(modifier = Modifier.padding(2.dp))

        Button(
            onClick = { onFavoriteClick(actor) },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background
            )
        ) {
            Text(
                text =
                if (actor.isInDatabase!!) {
                    stringResource(R.string.delete_favorite)
                } else {
                    stringResource(R.string.add_favorite)
                },
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}

@Composable
private fun Movies(actor: Actor) {
    Card(
        modifier = Modifier
            .padding(2.dp)
            .height(300.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.onSurface),
        backgroundColor = MaterialTheme.colors.background,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(Modifier.padding(2.dp)) {
            Text(
                text = stringResource(R.string.films),
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(2.dp)
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(5.dp)
            ) {
                actor.movies?.results?.let {
                    items(it.count()) { index ->
                        FilmItem(film = it[index])
                        Spacer(modifier = Modifier.padding(5.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun FilmItem(film: Film) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .width(150.dp),
            backgroundColor = MaterialTheme.colors.background,
            elevation = 3.dp,
            shape = MaterialTheme.shapes.small
        ) {
            Image(
                painter = rememberImagePainter(film.imageUrl(film.posterPath ?: "")),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                alignment = Alignment.Center,
                contentScale = ContentScale.FillWidth
            )
        }

        Text(
            text = film.originalTitle,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center
        )
    }
}

@ExperimentalPagerApi
@Composable
private fun Pager(actor: Actor) {
    Card(
        modifier = Modifier
            .padding(2.dp)
            .height(300.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.onSurface),
        backgroundColor = MaterialTheme.colors.background,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(Modifier.padding(2.dp)) {
            Text(
                text = stringResource(R.string.posters),
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(2.dp)
            )

            actor.imageList?.images?.let {
                HorizontalPager(
                    count = it.count(),
                    itemSpacing = 4.dp,
                    contentPadding = PaddingValues(4.dp)
                ) { page ->
                    Card(
                        modifier = Modifier
                            .height(280.dp),
                        backgroundColor = MaterialTheme.colors.background,
                        elevation = 3.dp,
                        shape = MaterialTheme.shapes.small
                    ) {
                        Image(
                            painter = rememberImagePainter(actor.imageUrl(it[page].filePath)),
                            contentDescription = null,
                            alignment = Alignment.Center,
                            contentScale = ContentScale.FillWidth
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ActorInfo(actor: Actor) {
    Card(
        modifier = Modifier.padding(2.dp),
        elevation = 4.dp,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.onSurface),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(Modifier.padding(3.dp)) {
            Text(
                text = actor.name,
                style = MaterialTheme.typography.h1,
                fontSize = 27.sp,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = actor.biography ?: "",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}

@Composable
private fun Poster(actor: Actor) {

    Card(
        modifier = Modifier
            .height(400.dp)
            .width(300.dp),
        backgroundColor = MaterialTheme.colors.background,
        elevation = 3.dp,
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.onSurface)
    ) {
        Image(
            painter = rememberImagePainter(actor.imageUrl(actor.profilePath ?: "")),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )
    }
}