package com.example.inostudioTask.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.inostudioTask.presentation.filmList.FilmListScreen
import com.example.inostudioTask.presentation.filmReview.FilmReviewScreen
import com.example.inostudioTask.presentation.ui.theme.MainTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalPagerApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val items = listOf(
                Screen.FilmsListScreen,
                Screen.ActorsListScreen,
                Screen.FavoritesListScreen
            )

            Surface(color = MaterialTheme.colors.background) {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigation {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination

                            items.forEach { screen ->
                                BottomNavigationItem(
                                    icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                                    label = null,
                                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )

                            }
                        }
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.FilmsListScreen.route
                    ) {
                        composable(
                            route = Screen.FilmsListScreen.route
                        ) {
                            MainTheme {
                                FilmListScreen(
                                    navController = navController
                                )
                            }
                        }
                        composable(
                            route = Screen.FilmReviewScreen.route + "/{movie_id}",
                            arguments = listOf(
                                navArgument("movie_id") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            MainTheme {
                                FilmReviewScreen(
                                    navController = navController,
                                    backStackEntry.arguments?.getString("movie_id")
                                )
                            }
                        }

//                        composable(
//                            route = Screen.ActorsListScreen.route
//                        ) {
//                            MainTheme {
//                                ActorsListScreen(
//                                    navController = navController
//                                )
//                            }
//                        }
//
//                        composable(
//                            route = Screen.FavoritesListScreen.route
//                        ) {
//                            MainTheme {
//                                FavoritesListScreen(
//                                    navController = navController
//                                )
//                            }
//                        }
                    }
                }
            }
        }
    }
}