package com.example.inostudioTask.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.inostudioTask.R
import com.example.inostudioTask.presentation.filmList.FilmListScreen
import com.example.inostudioTask.presentation.filmReview.FilmReviewScreen
import com.example.inostudioTask.presentation.ui.theme.MainTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
@ExperimentalPagerApi
@ExperimentalAnimationApi
@InternalCoroutinesApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            MainTheme {
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
                                        icon = {
                                            Icon(
                                                Icons.Filled.Favorite,
                                                contentDescription = null
                                            )
                                        },
                                        label = null,
                                        selected = currentDestination?.hierarchy?.any {
                                            it.route == screen.route
                                        } == true,
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                popUpTo(
                                                    navController.graph.findStartDestination().id
                                                ) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    )
                                }
                            }
                        },
                        topBar = {
                            TopAppBar(
                                modifier = Modifier.fillMaxWidth(),
                                title = { Text(text = context.getString(R.string.top_app_title)) },
                                navigationIcon = {
                                    Icon(
                                        Icons.Rounded.ArrowBackIos,
                                        contentDescription = null,
                                        Modifier
                                            .padding(5.dp)
                                            .fillMaxSize()
                                            .clickable {
                                                navController.popBackStack(
                                                    destinationId = navController
                                                        .graph
                                                        .findStartDestination()
                                                        .id,
                                                    inclusive = false
                                                )
                                            }
                                    )
                                }
                            )
                        }
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.FilmsListScreen.route
                        ) {
                            composable(
                                route = Screen.FilmsListScreen.route
                            ) {
                                FilmListScreen(
                                    navController = navController
                                )
                            }
                            composable(
                                route = "${Screen.FilmReviewScreen.route}/{movie_id}",
                                arguments = listOf(
                                    navArgument("movie_id") { type = NavType.StringType },
                                )
                            ) {
                                FilmReviewScreen()
                            }

//                        composable(
//                            route = Screen.ActorsListScreen.route
//                        ) {
//                                ActorsListScreen(
//                                    navController = navController
//                                )
//                        }
//
//                        composable(
//                            route = Screen.FavoritesListScreen.route
//                        ) {
//                                FavoritesListScreen(
//                                    navController = navController
//                                )
//                        }
                        }
                    }
                }
            }
        }
    }
}
