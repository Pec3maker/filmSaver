package com.example.inostudio_task.presentation.film_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.inostudio_task.common.Constants
import com.example.inostudio_task.domain.model.Film


@Composable
fun FilmListItem(
    film: Film,
    onItemClick: (Film) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(film) }
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = rememberImagePainter(Constants.IMAGE_PATH + film.posterPath),
            contentDescription = null,
            modifier = Modifier.size(128.dp)
        )
        Text(
            text = "${film.title}",
            style = MaterialTheme.typography.body1,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            textAlign = TextAlign.Left
        )
    }
}

