package com.example.inostudioTask.presentation.common.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.inostudioTask.R
import com.example.inostudioTask.data.remote.dto.Actor
import com.example.inostudioTask.data.remote.dto.profilePathUrl
import com.example.inostudioTask.data.remote.dto.profileUrl

@Composable
fun ActorListComponent(
    actorList: List<Actor>,
    navigate: (Int) -> Unit,
    addFavorite: (Actor) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        items(actorList) { actor ->
            ActorItem(
                actor = actor,
                onItemClick = { navigate(actor.id) },
                onFavoriteClick = { addFavorite(it) })
        }
    }
}

@Composable
fun ActorItem(
    actor: Actor,
    onItemClick: () -> Unit,
    onFavoriteClick: (Actor) -> Unit
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
                .clickable { onItemClick() },
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                elevation = 2.dp,
                modifier = Modifier.padding(start = 2.dp)
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
                        text = actor.name,
                        fontSize = 19.sp,
                        style = MaterialTheme.typography.h1,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                    )

                    IconButton(onClick = { onFavoriteClick(actor) }) {
                        Icon(
                            imageVector =
                            if (actor.isInDatabase!!) {
                                Icons.Filled.Favorite
                            } else {
                                Icons.Filled.FavoriteBorder
                            },
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = stringResource(id = R.string.popularity, actor.popularity),
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
        }
    }
}