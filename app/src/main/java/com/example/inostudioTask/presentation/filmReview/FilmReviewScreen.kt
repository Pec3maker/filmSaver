package com.example.inostudioTask.presentation.filmReview

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.ArrowCircleDown
import androidx.compose.material.icons.rounded.Error
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.inostudioTask.R
import com.example.inostudioTask.common.Constants
import com.example.inostudioTask.data.remote.dto.CastResponse
import com.example.inostudioTask.data.remote.dto.ImageResponse
import com.example.inostudioTask.domain.model.Film
import com.example.inostudioTask.presentation.filmReview.components.ActorItem
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager


@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun FilmReviewScreen(
    navController: NavController,
    movieId: String?,
    viewModel: FilmReviewViewModel = hiltViewModel(),
) {
    var editable by remember { mutableStateOf(false) }
    val state = viewModel.state.value
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        state.data?.let { data ->
            val film = data as Film

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                TopAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    title = { Text(text = context.getString(R.string.top_app_title)) },
                    navigationIcon = {
                        Icon(
                            Icons.Rounded.ArrowBackIos,
                            contentDescription = null,
                            Modifier
                                .padding(5.dp)
                                .fillMaxSize()
                                .clickable {
                                    navController.popBackStack()
                                }
                        )
                    }
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background),
                    contentPadding = PaddingValues(20.dp)
                ) {
                    item {
                        Image(
                            painter = rememberImagePainter(Constants.IMAGE_PATH + film.posterPath),
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

                        var images = emptyList<ImageResponse>()
                        film.images?.images?.let {
                            images = it
                        }
                        Text(
                            text = context.getString(R.string.posters),
                            style = MaterialTheme.typography.h1,
                            color = MaterialTheme.colors.onSurface
                        )
                        HorizontalPager(images.count()) { page ->
                            Image(
                                painter = rememberImagePainter(
                                    Constants.IMAGE_PATH + images[page].filePath
                                ),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(400.dp)
                                    .height(30.dp)
                            )
                        }

                        Spacer(modifier = Modifier.padding(15.dp))

                        var actors = emptyList<CastResponse>()
                        film.credits?.cast?.let {
                            actors = it
                        }
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
                            items(actors.count()) { index ->
                                ActorItem(actor = actors[index])
                                Spacer(modifier = Modifier.padding(5.dp))
                            }
                        }

                        if(film.reviews?.results?.isEmpty() == false) {
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


                        if(film.videos?.results?.isEmpty() == false) {
                            Button(
                                onClick = {

                                    val webIntent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(
                                            Constants.BASE_YOUTUBE_URL
                                                    + film.videos.results[0].key
                                        ),
                                    )
                                    context.startActivity(webIntent)
                                }
                            ) {
                                Text(
                                    text = context.getString(R.string.watchVideo),
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
        }

        if (state.error != null) {

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Rounded.Error,
                    contentDescription = null,
                    Modifier
                        .padding(5.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.padding(5.dp))

                Text(
                    text = context.getString(state.error as Int),
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.padding(10.dp))

                Button(
                    onClick = {
                        movieId?.let { viewModel.refresh(movieId) }
                    }
                ) {
                    Text(
                        text = context.getString(R.string.refresh_string),
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}