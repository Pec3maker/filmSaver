package com.example.inostudioTask.presentation.filmList.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.inostudioTask.common.Constants
import com.example.inostudioTask.data.remote.dto.Film
import com.example.inostudioTask.presentation.common.ExtraInfo

@Composable
fun FilmListItem(
    film: Film,
    onItemClick: (Film) -> Unit,
    onFavoriteClick: (Film) -> Unit
) {
    Card(
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.primary),
        backgroundColor = MaterialTheme.colors.background,
        elevation = 3.dp,
        modifier = Modifier
            .padding(5.dp)
            .height(130.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick(film) },
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                elevation = 2.dp,
                modifier = Modifier.padding(start = 2.dp)
            ) {
                Image(
                    painter = rememberImagePainter(
                        "${Constants.IMAGE_PATH}${film.posterPath}"
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width = 110.dp, height = 125.dp)
                )
            }

            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(start = 5.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = film.title,
                        fontSize = 19.sp,
                        style = MaterialTheme.typography.h1,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                    )

                    Icon(
                        imageVector =
                        if (film.isInDatabase!!) {
                            Icons.Filled.Favorite
                        } else {
                            Icons.Filled.FavoriteBorder
                        },
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { onFavoriteClick(film) }
                            .size(30.dp)
                    )
                }

                Text(
                    text = film.overview,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 2
                )

                Row(verticalAlignment = Alignment.Bottom) {
                    ExtraInfo(film = film)
                }
            }
        }
    }
}