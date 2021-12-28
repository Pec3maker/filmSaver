package com.example.inostudioTask.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector) {
    object FilmsListScreen: Screen("film_list_screen", Icons.Filled.Home)
    object FilmReviewScreen: Screen("film_review_screen", Icons.Filled.Movie)
    object CastListScreen: Screen("cast_list_screen", Icons.Filled.Person)
    object FavoriteFilmListScreen: Screen("favorites_list_screen", Icons.Filled.Favorite)
    object FilmReviewListScreen: Screen("film_review_list_screen", Icons.Filled.Reviews)
    object ActorReviewScreen: Screen("actor_review_screen", Icons.Filled.Reviews)
}