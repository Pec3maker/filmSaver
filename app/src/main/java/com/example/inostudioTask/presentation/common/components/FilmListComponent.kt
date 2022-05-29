package com.example.inostudioTask.presentation.common.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.inostudioTask.data.remote.dto.Film
import com.example.inostudioTask.presentation.ui.theme.Gray150
import com.example.inostudioTask.presentation.ui.theme.Gray300
import com.example.inostudioTask.presentation.ui.theme.Red500

@Composable
fun FilmListComponent(
    filmList: List<Film>,
    navigate: (Int) -> Unit,
    addFavorite: (Film) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 13.dp),
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

@Composable
fun FilmListItem(
    film: Film,
    onItemClick: (Film) -> Unit,
    onFavoriteClick: (Film) -> Unit
) {
    Card(
        border = BorderStroke(width = 1.dp, color = Gray150),
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        modifier = Modifier
            .aspectRatio(ratio = 1.23f)
            .clickable { onItemClick(film) },
        shape = RoundedCornerShape(size = 8.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(all = 4.dp)
        ) {
            Image(
                painter = rememberImagePainter(film.posterPathUrl()),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(ratio = 1.8f)
                    .clip(shape = RoundedCornerShape(size = 8.dp))
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = film.title,
                    style = MaterialTheme.typography.h5,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .fillMaxWidth(0.7f),
                    color = MaterialTheme.colors.onSurface
                )

                IconButton(onClick = { onFavoriteClick(film) }) {
                    Icon(
                        painter = rememberImagePainter(R.drawable.ic_like),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = if (film.isInDatabase!!) {
                            Red500
                        } else {
                            Gray150
                        }
                    )
                }
            }

            Text(
                text = stringResource(R.string.avg_rating, String.format("%.1f", film.voteAverage)),
                style = MaterialTheme.typography.subtitle1,
                color = Gray300
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}