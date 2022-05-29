package com.example.inostudioTask.presentation.actorReview.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.inostudioTask.R
import com.example.inostudioTask.data.remote.dto.Actor
import com.example.inostudioTask.data.remote.dto.Film
import com.example.inostudioTask.presentation.ui.theme.Gray150
import com.example.inostudioTask.presentation.ui.theme.Gray300
import com.example.inostudioTask.presentation.ui.theme.Red500
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager

@ExperimentalPagerApi
@Composable
fun ActorReviewSuccessScreen(
    actor: Actor,
    onFavoriteClick: (Actor) -> Unit,
    onFilmClick: (Int) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
    ) {
        Image(
            painter =
            if (actor.profilePath.isNullOrEmpty()) {
                rememberImagePainter(data = R.drawable.not_found_image)
            } else {
                rememberImagePainter(actor.profilePathUrl())
            },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = 0.8f)
                .clip(shape = RoundedCornerShape(size = 8.dp))
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = actor.name,
                style = MaterialTheme.typography.h5,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .fillMaxWidth(0.7f),
                color = MaterialTheme.colors.onSurface
            )

            IconButton(onClick = { onFavoriteClick(actor) }) {
                Icon(
                    painter = rememberImagePainter(R.drawable.ic_like),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = if (actor.isInDatabase!!) {
                        Red500
                    } else {
                        Gray150
                    }
                )
            }
        }
        Text(
            text = stringResource(R.string.popularity, String.format("%.1f", actor.popularity)),
            style = MaterialTheme.typography.subtitle1,
            color = Gray300
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = actor.biography ?: "",
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(32.dp))
        actor.imageList?.images?.let {
            HorizontalPager(
                count = it.count(),
                itemSpacing = 4.dp,
                contentPadding = PaddingValues(4.dp)
            ) { page ->
                Image(
                    painter = rememberImagePainter(actor.imageList.images[page].url()),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(ratio = 0.8f)
                        .clip(shape = RoundedCornerShape(size = 8.dp)),
                    contentDescription = null,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            actor.movies?.results?.let { it ->
                items(it.count()) { index ->
                    FilmItem(film = it[index]) { id -> onFilmClick(id) }
                    Spacer(modifier = Modifier.padding(5.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun FilmItem(
    film: Film,
    onFilmClick: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .clickable { onFilmClick(film.id) }
    ) {
        Card(
            modifier = Modifier
                .width(150.dp)
                .height(225.dp),
            backgroundColor = MaterialTheme.colors.background,
            elevation = 3.dp,
            shape = MaterialTheme.shapes.small
        ) {
            Image(
                painter =
                if (film.posterPath.isNullOrEmpty()) {
                    rememberImagePainter(data = R.drawable.not_found_image)
                } else {
                    rememberImagePainter(film.posterPathUrl())
                },
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                alignment = Alignment.Center,
                contentScale = ContentScale.FillHeight
            )
        }

        Text(
            text = film.originalTitle,
            style = MaterialTheme.typography.subtitle1,
            color = Gray300,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(width = 150.dp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}