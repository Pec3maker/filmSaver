package com.example.inostudioTask.presentation.filmReview.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.inostudioTask.common.Constants
import com.example.inostudioTask.data.remote.dto.CastResponse

@Composable
fun ActorItem(actor: CastResponse) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberImagePainter(
                Constants.IMAGE_PATH + actor.profilePath
            ),
            contentDescription = null,
            modifier = Modifier
                .size(400.dp)
                .height(30.dp),
            alignment = Alignment.Center
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = actor.name,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = actor.character,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center
        )
    }
}