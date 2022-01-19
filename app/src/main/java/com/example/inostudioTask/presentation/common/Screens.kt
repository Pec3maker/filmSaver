package com.example.inostudioTask.presentation.common

sealed class Screens(val route: String) {
    object FilmsListScreen: Screens("film_list_screen")
    object CastListScreen: Screens("cast_list_screen")
    object FavoriteListScreen: Screens("favorites_list_screen")
    object FilmReviewListScreen : Screens("film_review_list_screen")
    object ActorReviewScreen : Screens("actor_review_screen")
    object FilmReviewScreen : Screens("film_review_screen")
}