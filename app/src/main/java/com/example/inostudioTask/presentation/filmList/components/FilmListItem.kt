package com.example.inostudioTask.presentation.filmList.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.inostudioTask.common.Constants
import com.example.inostudioTask.domain.model.Film


@Composable
fun FilmListItem(
    film: Film,
    onItemClick: (Film) -> Unit,
    onFavoriteClick: (Film) -> Unit,
    textButton: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(film) }
            .padding(20.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = rememberImagePainter(Constants.IMAGE_PATH + film.posterPath),
            contentDescription = null,
            modifier = Modifier.size(128.dp)
        )
        Column {
            Text(
                text = film.title,
                style = MaterialTheme.typography.body1,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                textAlign = TextAlign.Left
            )
            Spacer(modifier = Modifier.padding(3.dp))

            Button(
                onClick = {
                    onFavoriteClick(film)
                }
            ) {
                Text(
                    text = textButton,
                    style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

