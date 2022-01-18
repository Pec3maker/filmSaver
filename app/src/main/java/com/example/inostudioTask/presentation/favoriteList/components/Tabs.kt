package com.example.inostudioTask.presentation.favoriteList.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.inostudioTask.R

sealed class Tabs(val text: Int, val icon: ImageVector) {
    object FilmsScreen: Tabs(text = R.string.films, icon = Icons.Filled.Movie)
    object ActorsScreen: Tabs(text = R.string.actors, icon = Icons.Filled.Person)
}