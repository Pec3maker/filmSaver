package com.example.inostudioTask.presentation

sealed class Screen(val route: String) {
    object FilmListScreen: Screen("film_list_screen")
    object FilmReviewScreen: Screen("film_review_screen")
}
