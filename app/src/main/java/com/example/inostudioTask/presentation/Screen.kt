package com.example.inostudioTask.presentation

sealed class Screen(val route: String) {
    object FilmsListScreen: Screen("film_list_screen")
    object FilmReviewScreen: Screen("film_review_screen")
    object ActorsListScreen: Screen("actor_list_screen")
    object FavoritesListScreen: Screen("favorites_list_screen")
}
