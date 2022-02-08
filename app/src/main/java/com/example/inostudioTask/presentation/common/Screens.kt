package com.example.inostudioTask.presentation.common

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screens(val route: String, val title: String) {

    object FilmsListScreen : Screens("film_list_screen", "Films")
    object CastListScreen : Screens("cast_list_screen", "Cast")
    object FavoriteListScreen : Screens("favorites_list_screen", "Favorites")

    class FilmReviewListScreen() : Screens(
        route = route,
        title = "Reviews"
    ) {
        companion object {
            private const val ROUTE: String = "film_review_list_screen"
            const val NAV_ARGUMENT_NAME: String = "movie_id"
            const val route = "$ROUTE/{$NAV_ARGUMENT_NAME}"
            val arguments = listOf(navArgument(NAV_ARGUMENT_NAME) { type = NavType.StringType })

            fun getNavigationRoute(movieId: Int) = "$ROUTE/$movieId"
        }
    }

    class ActorReviewScreen() : Screens(
        route = route,
        title = "Actor review"
    ) {
        companion object {
            private const val ROUTE: String = "actor_review_screen"
            const val NAV_ARGUMENT_NAME: String = "actor_id"
            const val route = "$ROUTE/{$NAV_ARGUMENT_NAME}"
            val arguments = listOf(navArgument(NAV_ARGUMENT_NAME) { type = NavType.StringType })

            fun getNavigationRoute(movieId: Int) = "$ROUTE/$movieId"
        }
    }

    class FilmReviewScreen() : Screens(
        route = route,
        title = "Film review"
    ) {
        companion object {
            private const val ROUTE: String = "film_review_screen"
            const val NAV_ARGUMENT_NAME: String = "movie_id"
            const val route = "$ROUTE/{$NAV_ARGUMENT_NAME}"
            val arguments = listOf(navArgument(NAV_ARGUMENT_NAME) { type = NavType.StringType })

            fun getNavigationRoute(movieId: Int) = "$ROUTE/$movieId"
        }
    }

    companion object {
        fun fromRoute(route: String?): Screens =
            when (route) {
                FilmsListScreen.route -> FilmsListScreen
                CastListScreen.route -> CastListScreen
                FavoriteListScreen.route -> FavoriteListScreen
                FilmReviewListScreen.route -> FilmReviewListScreen()
                ActorReviewScreen.route -> ActorReviewScreen()
                FilmReviewScreen.route -> FilmReviewScreen()
                null -> FilmsListScreen
                else -> throw IllegalArgumentException("Route $route is not recognized")
            }
    }
}