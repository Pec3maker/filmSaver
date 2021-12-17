package com.example.inostudioTask.presentation.filmReview.components

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowCircleDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.inostudioTask.R
import com.example.inostudioTask.data.remote.dto.Film
import com.example.inostudioTask.data.remote.dto.imageUrl
import com.example.inostudioTask.data.remote.dto.toCombinedImages
import com.example.inostudioTask.data.remote.dto.videoUrl
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun FilmReviewSuccessScreen(
    film: Film,
    onFavoriteClick: (Film) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Card(
        modifier = Modifier
            .fillMaxHeight(0.9f)
            .padding(2.dp),
        backgroundColor = MaterialTheme.colors.background,
        elevation = 5.dp,
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(2.dp)
        ) {
            Image(
                painter = rememberImagePainter(film.imageUrl(film.posterPath ?: "")),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .height(300.dp)
            )

            Text(
                text = film.originalTitle,
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = film.overview,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.padding(15.dp))

            Text(
                text = stringResource(R.string.posters),
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onSurface
            )

            film.images?.toCombinedImages()?.let {
                HorizontalPager(it.count()) { page ->
                    Image(
                        painter = rememberImagePainter(film.imageUrl(it[page].filePath)),
                        contentDescription = null,
                        modifier = Modifier
                            .size(400.dp)
                            .height(30.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.padding(15.dp))

            Text(
                text = stringResource(R.string.actors),
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onSurface
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(5.dp)
            ) {
                film.credits?.cast?.let {
                    items(it.count()) { index ->
                        ActorItem(actor = it[index])
                        Spacer(modifier = Modifier.padding(5.dp))
                    }
                }
            }

            if (film.reviews?.results?.isEmpty() == false) {
                Spacer(modifier = Modifier.padding(15.dp))
                Row(
                    Modifier.fillMaxSize()
                ) {
                    Text(
                        stringResource(R.string.review),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface
                    )

                    Icon(
                        Icons.Rounded.ArrowCircleDown,
                        contentDescription = null,
                        Modifier
                            .padding(7.dp)
                            .fillMaxSize()
                            .clickable {
                                expanded = !expanded
                            },
                    )
                }

                Text(
                    text = film.reviews.results[0].content,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.animateContentSize(),
                    maxLines = if (expanded) Int.MAX_VALUE else film.linesToShow
                )

                Spacer(modifier = Modifier.padding(15.dp))
            }

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { onFavoriteClick(film) }
                ) {
                    Text(
                        text =
                        if (film.isInDatabase!!) {
                            stringResource(R.string.delete_favorite)
                        } else {
                            stringResource(R.string.add_favorite)
                        },
                        color = MaterialTheme.colors.onSurface
                    )
                }
                if (film.videos?.results?.isEmpty() == false) {
                    Button(
                        onClick = {
                            val webIntent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(film.videoUrl())
                            )
                            context.startActivity(webIntent)
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.watch_video),
                            color = MaterialTheme.colors.onSurface
                        )
                    }
                }
            }

            Text(
                text = stringResource(R.string.release_date, film.releaseDate ?: ""),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = stringResource(R.string.avg_rating, film.voteAverage),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}