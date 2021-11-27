package com.example.inostudioTask.presentation.filmReview.components

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowCircleDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.inostudioTask.R
import com.example.inostudioTask.common.Constants
import com.example.inostudioTask.domain.model.Film
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun FilmReviewSuccessScreen(
    film: Film
) {
    var editable by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = rememberImagePainter(
                    context.getString(
                        R.string.path,
                        Constants.image_path,
                        film.posterPath
                    )
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .height(300.dp)
            )

            Text(
                text = film.title,
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.height(30.dp))

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
                text = context.getString(R.string.posters),
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onSurface
            )

            film.images?.count()?.let {
                HorizontalPager(it) { page ->
                    Image(
                        painter = rememberImagePainter(
                            context.getString(
                                R.string.path,
                                Constants.image_path,
                                film.images[page].filePath
                            )
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(400.dp)
                            .height(30.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.padding(15.dp))

            Text(
                text = context.getString(R.string.actors),
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onSurface
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background),
                contentPadding = PaddingValues(20.dp)
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
                        context.getString(R.string.review),
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
                                editable = !editable
                            },
                    )
                }

                AnimatedVisibility(visible = editable) {
                    Text(
                        text = film.reviews.results[0].content,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface
                    )
                }
                Spacer(modifier = Modifier.padding(15.dp))
            }

            if (film.videos?.results?.isEmpty() == false) {
                Button(
                    onClick = {
                        val webIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(
                                context.getString(
                                    R.string.path,
                                    Constants.base_youtube_url,
                                    film.videos.results[0].key
                                )
                            ),
                        )
                        context.startActivity(webIntent)
                    }
                ) {
                    Text(
                        text = context.getString(R.string.watch_video),
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }

            Text(
                text = context.getString(R.string.release_date, film.releaseDate),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = context.getString(R.string.avg_rating, film.voteAverage),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}