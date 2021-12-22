package com.example.inostudioTask.presentation.mainActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
import com.example.inostudioTask.presentation.common.Screen
import com.example.inostudioTask.presentation.castList.CastListScreen
import com.example.inostudioTask.presentation.favoriteFilmList.FavoriteFilmListScreen
import com.example.inostudioTask.presentation.filmList.FilmListScreen
import com.example.inostudioTask.presentation.filmOverview.FilmOverviewScreen
import com.example.inostudioTask.presentation.filmReviewList.FilmReviewListScreen
import com.example.inostudioTask.presentation.ui.theme.MainTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagerApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainTheme {
                MainScreen()
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomNavigationBar(
                navigate = { route ->
                    navController.navigate(route) {
                        popUpTo(
                            navController.graph.findStartDestination().id
                        ) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                isSelected = { route ->
                    currentDestination?.hierarchy?.any {
                        it.route == route
                    } == true
                }
            )
        },
        topBar = {
            if (currentDestination?.route != Screen.FilmsListScreen.route) {
                TopBar {
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
            }
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.FilmsListScreen.route
            ) {
                composable(
                    route = Screen.FilmsListScreen.route
                ) {
                    FilmListScreen(navController = navController) {
                        scaffoldState.snackbarHostState.showSnackbar(it)
                    }
                }

                composable(
                    route = "${Screen.FilmReviewScreen.route}/{movie_id}",
                    arguments = listOf(
                        navArgument("movie_id") { type = NavType.StringType },
                    )
                ) {
                    FilmOverviewScreen(navController = navController)
                }

                composable(
                    route = Screen.CastListScreen.route
                ) {
                    CastListScreen()
                }

                composable(
                    route = Screen.FavoriteFilmListScreen.route
                ) {
                    FavoriteFilmListScreen()
                }

                composable(
                    route = "${Screen.FilmReviewListScreen.route}/{movie_id}",
                    arguments = listOf(
                        navArgument("movie_id") { type = NavType.StringType },
                    )
                ) {
                    FilmReviewListScreen()
                }
            }
        }
    }
}

@Composable
fun TopBar(
    navigationIcon: @Composable () -> Unit,
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = { Text(text = stringResource(R.string.top_app_title)) },
        navigationIcon = { navigationIcon() },
        backgroundColor = MaterialTheme.colors.onSecondary
    )
}

@Composable
fun BottomNavigationBar (
    navigate: (String) -> Unit,
    isSelected: (String) -> Boolean
) {
    val items = listOf(
        Screen.FilmsListScreen,
        Screen.CastListScreen,
        Screen.FavoriteFilmListScreen
    )

    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.onSecondary
    ) {
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = null
                    )
                },
                label = null,
                selected = isSelected(screen.route),
                onClick = { navigate(screen.route) }
            )
        }
    }
}