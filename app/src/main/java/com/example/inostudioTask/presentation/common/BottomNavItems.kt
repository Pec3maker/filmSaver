package com.example.inostudioTask.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItems(val icon: ImageVector, val route: String) {
    FILMS(icon = Icons.Filled.Home, route = "films"),
    CAST(icon = Icons.Filled.Person, route = "cast"),
    FAVORITES(icon = Icons.Filled.Favorite, route = "favorites")
}