package com.example.inostudioTask.presentation.filmOverview.components

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalContext
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
            .padding(horizontal = 16.dp)
    ) {
        Image(
            painter = rememberImagePainter(film.posterPathUrl()),
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
                text = film.title,
                style = MaterialTheme.typography.h5,
                overflow = TextOverflow.Ellipsis,
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
        Text(
            text = stringResource(R.string.release_date, film.releaseDate ?: ""),
            style = MaterialTheme.typography.subtitle1,
            color = Gray300
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = film.overview,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(32.dp))
        film.images?.toCombinedImages()?.let {
            HorizontalPager(
                count = it.count(),
                itemSpacing = 4.dp
            ) { page ->
                Image(
                    painter = rememberImagePainter(film.imageUrl(it[page].filePath)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(ratio = 1.8f)
                        .clip(shape = RoundedCornerShape(size = 8.dp)),
                    contentDescription = null,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            film.credits?.cast?.let { actorList ->
                items(actorList.count()) { index ->
                    ActorItem(actor = actorList[index]) { onActorClick(it) }
                    Spacer(modifier = Modifier.padding(5.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Buttons(
            onReviewClick = { onReviewClick() },
            film = film
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
private fun Buttons(
    film: Film,
    onReviewClick: () -> Unit
) {
    val context = LocalContext.current
    Row(modifier = Modifier.fillMaxWidth()) {
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
                    backgroundColor = MaterialTheme.colors.primary
                ),
                modifier = Modifier.height(height = 48.dp)
            ) {
                Text(
                    text = stringResource(R.string.watch_video),
                    color = MaterialTheme.colors.background,
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }
        Spacer(modifier = Modifier.width(width = 23.dp))
        if ((film.reviews?.results?.size ?: 0) > 1) {
            Button(
                onClick = {
                    onReviewClick()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary
                ),
                modifier = Modifier.height(height = 48.dp)
            ) {
                Text(
                    text = stringResource(R.string.review_list),
                    color = MaterialTheme.colors.background,
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }
    }
}