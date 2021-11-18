package com.example.inostudioTask.presentation.filmReview

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.inostudioTask.R
import com.example.inostudioTask.common.Constants
import com.example.inostudioTask.domain.model.Film

@Composable
fun FilmReviewScreen(
    navController: NavController,
    viewModel: FilmReviewViewModel = hiltViewModel(),
    context: Context
) {
    val state = viewModel.state.value
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
                            text = film.title ?: "",
                            style = MaterialTheme.typography.h1,
                            color = MaterialTheme.colors.onSurface
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        Text(
                            text = film.originalTitle ?: "",
                            style = MaterialTheme.typography.h1,
                            color = MaterialTheme.colors.onSurface
                        )
                        Spacer(modifier = Modifier.height(15.dp))

                        Text(
                            text = film.overview ?: "",
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onSurface
                        )

                        Image(
                            painter = rememberImagePainter(
                                Constants.IMAGE_PATH + film.backdropPath
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .size(400.dp)
                                .height(30.dp)
                        )
                        Text(
                            text = context.getString(R.string.release_date) + film.releaseDate,
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onSurface
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = context.getString(R.string.avg_rating)
                                    + film.voteAverage.toString(),
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onSurface
                        )
                    }
                }
            }


            if (state.error != null) {
                Text(
                    text = context.getString(state.error as Int),
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}