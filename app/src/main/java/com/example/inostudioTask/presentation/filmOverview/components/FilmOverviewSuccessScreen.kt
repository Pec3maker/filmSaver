package com.example.inostudioTask.presentation.filmOverview.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.inostudioTask.R
import com.example.inostudioTask.data.remote.dto.Film
import com.example.inostudioTask.data.remote.dto.posterPathUrl
import com.example.inostudioTask.data.remote.dto.toCombinedImages
import com.example.inostudioTask.data.remote.dto.videoUrl
import com.example.inostudioTask.presentation.common.components.ExtraInfo
import com.example.inostudioTask.presentation.common.components.LikeButton
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun FilmOverviewSuccessScreen(
    film: Film,
    onFavoriteClick: (Film) -> Unit,
    onReviewClick: () -> Unit,
    onActorClick: (Int) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        FilmPoster(film)

        Spacer(modifier = Modifier.height(2.dp))

        FilmInfo(film)

        Spacer(modifier = Modifier.height(2.dp))

        FilmImages(film)

        Spacer(modifier = Modifier.height(2.dp))

        Actors(film) { onActorClick(it) }

        Spacer(modifier = Modifier.height(2.dp))

        Review(film)

        Spacer(modifier = Modifier.height(2.dp))

        Buttons(
            onFavoriteClick = { onFavoriteClick(film) },
            onReviewClick = { onReviewClick() },
            film = film
        )

        Spacer(modifier = Modifier.height(2.dp))

        ExtraInfo(film)
    }
}

@Composable
private fun Buttons(
    onFavoriteClick: () -> Unit,
    film: Film,
    onReviewClick: () -> Unit
) {
    val context = LocalContext.current
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        LikeButton(isInDatabase = film.isInDatabase!!) { onFavoriteClick() }

        if (film.videos?.results?.isEmpty() == false) {
            Button(
                onClick = {
                    val webIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(film.videoUrl())
                    )
                    context.startActivity(webIntent)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.background
                )
            ) {
                Text(
                    text = stringResource(R.string.watch_video),
                    color = MaterialTheme.colors.onSurface
                )
            }
        }

        if (film.reviews?.results?.size ?: 0 > 1) {
            Button(
                onClick = {
                    onReviewClick()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.background
                )
            ) {
                Text(
                    text = stringResource(R.string.review_list),
                    color = MaterialTheme.colors.onSurface
                )
            }
        }
    }
}

@Composable
private fun Review(
    film: Film
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier.padding(2.dp),
        elevation = 4.dp,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.onSurface),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column {
            if (film.reviews?.results?.isEmpty() == false) {
                Spacer(modifier = Modifier.padding(2.dp))
                Row(
                    Modifier.fillMaxSize()
                ) {
                    Text(
                        stringResource(R.string.review),
                        style = MaterialTheme.typography.h1,
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.padding(2.dp)
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
                    modifier = Modifier
                        .animateContentSize()
                        .padding(2.dp),
                    maxLines = if (expanded) Int.MAX_VALUE else film.linesToShow
                )
            }
        }
    }
}

@Composable
private fun Actors(
    film: Film,
    onActorClick: (Int) -> Unit
) {
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
                text = stringResource(R.string.actors),
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
                film.credits?.cast?.let { actorList ->
                    items(actorList.count()) { index ->
                        ActorItem(actor = actorList[index]) { onActorClick(it) }
                        Spacer(modifier = Modifier.padding(5.dp))
                    }
                }
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
private fun FilmImages(film: Film) {
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

            film.images?.toCombinedImages()?.let {
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
                            modifier = Modifier.fillMaxWidth(),
                            painter = rememberImagePainter(film.imageUrl(it[page].filePath)),
                            contentDescription = null,
                            alignment = Alignment.Center,
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FilmInfo(film: Film) {
    Card(
        modifier = Modifier.padding(2.dp),
        elevation = 4.dp,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.onSurface),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(Modifier.padding(3.dp)) {
            Text(
                text = film.originalTitle,
                style = MaterialTheme.typography.h1,
                fontSize = 27.sp,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = film.overview,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}

@Composable
private fun FilmPoster(film: Film) {
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
            painter = rememberImagePainter(film.posterPathUrl()),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )
    }
}