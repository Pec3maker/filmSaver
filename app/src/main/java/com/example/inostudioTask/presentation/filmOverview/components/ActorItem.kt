package com.example.inostudioTask.presentation.filmOverview.components

import androidx.compose.foundation.Image
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
import com.example.inostudioTask.data.remote.dto.CastResponse
import com.example.inostudioTask.data.remote.dto.profileUrl

@Composable
fun ActorItem(actor: CastResponse) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .width(150.dp),
            backgroundColor = MaterialTheme.colors.background,
            elevation = 3.dp,
            shape = MaterialTheme.shapes.small
        ) {
            Image(
                painter = rememberImagePainter(actor.profileUrl()),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                alignment = Alignment.Center,
                contentScale = ContentScale.FillWidth
            )
        }

        Text(
            text = actor.name ?: "",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center
        )
    }
}
