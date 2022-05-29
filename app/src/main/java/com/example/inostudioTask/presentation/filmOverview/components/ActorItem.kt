package com.example.inostudioTask.presentation.filmOverview.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.inostudioTask.R
import com.example.inostudioTask.data.remote.dto.CastResponse
import com.example.inostudioTask.presentation.ui.theme.Gray300

@Composable
fun ActorItem(
    actor: CastResponse,
    onActorClick: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .clickable { onActorClick(actor.id) }
    ) {
        Card(
            modifier = Modifier
                .width(150.dp)
                .height(225.dp),
            backgroundColor = MaterialTheme.colors.background,
            elevation = 3.dp,
            shape = MaterialTheme.shapes.small
        ) {
            Image(
                painter =
                if (actor.profilePath.isNullOrEmpty()) {
                    rememberImagePainter(data = R.drawable.not_found_image)
                } else {
                    rememberImagePainter(actor.profileUrl())
                },
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillHeight
            )
        }

        Text(
            text = actor.name ?: "",
            style = MaterialTheme.typography.subtitle1,
            color = Gray300,
            textAlign = TextAlign.Center
        )
    }
}