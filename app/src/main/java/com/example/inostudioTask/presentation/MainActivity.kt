package com.example.inostudioTask.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.inostudioTask.presentation.filmList.FilmListScreen
import com.example.inostudioTask.presentation.filmReview.FilmReviewScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            Surface(color = MaterialTheme.colors.background) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.FilmListScreen.route
                ) {
                    composable(
                        route = Screen.FilmListScreen.route
                    ) {
                        FilmListScreen(navController = navController, context = application.applicationContext)
                    }
                    composable(
                        route = Screen.FilmReviewScreen.route + "/{movie_id}"
                    ) {
                        FilmReviewScreen(context = application.applicationContext)
                    }
                }
            }
        }
    }
}

