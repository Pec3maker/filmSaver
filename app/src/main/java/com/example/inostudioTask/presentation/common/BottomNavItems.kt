package com.example.inostudioTask.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItems(val icon: ImageVector, val text: String) {
    FILMS(icon = Icons.Filled.Home, text = "films"),
    CAST(icon = Icons.Filled.Person, text = "cast"),
    FAVORITES(icon = Icons.Filled.Favorite, text = "favorites")
}