package com.example.inostudioTask.presentation.filmReview

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.inostudioTask.R
import com.example.inostudioTask.common.Constants

@Composable
fun FilmReviewScreen(
    viewModel: FilmReviewViewModel = hiltViewModel(),
    app: Application = Application()
) {
    val state = viewModel.state.value
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        state.film?.let { film ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray),
                contentPadding = PaddingValues(20.dp)
            ) {
                item {
                    Image(
                        painter = rememberImagePainter(Constants.IMAGE_PATH + film.posterPath),
                        contentDescription = null,
                        modifier = Modifier.size(300.dp)
                            .align(Alignment.Center)
                            .fillMaxSize()
                    )
                    Text(
                        text = film.title ?: "",
                        style = MaterialTheme.typography.h4,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = film.originalTitle ?: "",
                        style = MaterialTheme.typography.h5,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = film.overview ?: "",
                        style = MaterialTheme.typography.body1,
                        color = Color.White
                    )

                    Image(
                        painter = rememberImagePainter(
                            Constants.IMAGE_PATH + film.backdropPath
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(400.dp)
                            .height(30.dp)
                            .align(Alignment.Center)
                    )
                    Text(
                        text = app.getString(R.string.release_date) + film.releaseDate,
                        style = MaterialTheme.typography.body2,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = app.getString(R.string.avg_rating)
                            + film.voteAverage.toString(),
                        style = MaterialTheme.typography.body2,
                        color = Color.White
                    )
                }
            }


            if (state.error.isNotBlank()) {
                Text(
                    text = state.error,
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