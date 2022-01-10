package com.example.inostudioTask.presentation.common.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.inostudioTask.R

@Composable
fun LikedItemText(isInDatabase: Boolean) {
    Text(
        text = stringResource(
            id = if (isInDatabase) {
                R.string.delete_favorite
            } else {
                R.string.add_favorite
            }
        ),
        color = MaterialTheme.colors.onSurface
    )
}

