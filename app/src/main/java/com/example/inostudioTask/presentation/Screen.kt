package com.example.inostudioTask.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector) {
    object FilmsListScreen: Screen("film_list_screen", Icons.Filled.Home)
    object FilmReviewScreen: Screen("film_review_screen", Icons.Filled.Movie)
    object ActorsListScreen: Screen("actor_list_screen", Icons.Filled.Person)
    object FavoritesListScreen: Screen("favorites_list_screen", Icons.Filled.Favorite)
    object ReviewListScreen: Screen("review_list_screen", Icons.Filled.Reviews)
}