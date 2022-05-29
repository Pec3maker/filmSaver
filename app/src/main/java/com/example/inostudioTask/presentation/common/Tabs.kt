package com.example.inostudioTask.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.inostudioTask.R

enum class Tabs(val text: Int, val icon: ImageVector) {
    FILMS(text = R.string.films, icon = Icons.Filled.Movie),
    ACTORS(text = R.string.actors, icon = Icons.Filled.Person)
}