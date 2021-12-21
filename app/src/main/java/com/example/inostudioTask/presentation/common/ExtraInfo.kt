package com.example.inostudioTask.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.inostudioTask.R
import com.example.inostudioTask.data.remote.dto.Film

@Composable
fun ExtraInfo(film: Film) {
    Column {
        Text(
            text = stringResource(R.string.release_date, film.releaseDate ?: ""),
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier
        )

        Text(
            text = stringResource(R.string.avg_rating, film.voteAverage),
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier
        )
    }
}