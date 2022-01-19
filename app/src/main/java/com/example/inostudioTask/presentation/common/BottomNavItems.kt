package com.example.inostudioTask.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.inostudioTask.R

enum class BottomNavItems(val icon: ImageVector, val text: Int) {
    FILMS(icon = Icons.Filled.Home, text = R.string.film_icon),
    CAST(icon = Icons.Filled.Person, text = R.string.cast_icon),
    FAVORITE(icon = Icons.Filled.Favorite, text = R.string.favorites_icon)
}