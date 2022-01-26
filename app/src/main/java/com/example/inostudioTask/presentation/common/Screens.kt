package com.example.inostudioTask.presentation.common

enum class Screens(val route: String, val text: String) {
    FILM_LIST("film_list_screen", "Films"),
    CAST_LIST("cast_list_screen", "Cast"),
    FAVORITES("favorites_list_screen", "Favorites"),
    FILMS_REVIEWS("film_review_list_screen", "Reviews"),
    ACTOR_REVIEW("actor_review_screen", "Actor review"),
    FILM_REVIEW("film_review_screen", "Film review")
}