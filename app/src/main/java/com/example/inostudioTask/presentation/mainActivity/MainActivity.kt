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
import com.example.inostudioTask.presentation.actorReview.ActorReviewScreen
import com.example.inostudioTask.presentation.common.Screens
import com.example.inostudioTask.presentation.castList.CastListScreen
import com.example.inostudioTask.presentation.common.BottomNavItems
import com.example.inostudioTask.presentation.favoriteList.FavoriteListScreen
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
    val barScreens = listOf(
        Screens.FilmsListScreen,
        Screens.CastListScreen,
        Screens.FavoriteListScreen
    )

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomNavigationBar(
                navigate = { route ->
                    if (
                        currentDestination?.hierarchy?.any {
                            it.route == route
                        } == false
                    ) {
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id)
                        }
                    }
                },
                isSelected = { route ->
                    currentDestination?.hierarchy?.any {
                        it.route == route
                    } == true
                },
                screens = barScreens
            )
        },
        topBar = {
            TopBar(
                navigationIcon =
                if (barScreens.none { it.route == currentDestination?.route }) {
                    {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIos,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxSize()
                                .clickable {
                                    navController.popBackStack()
                                }
                        )
                    }
                } else {
                    null
                }
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = Screens.FilmsListScreen.route
            ) {
                composable(
                    route = Screens.FilmsListScreen.route
                ) {
                    FilmListScreen(navController = navController) {
                        scaffoldState.snackbarHostState.showSnackbar(it)
                    }
                }

                composable(
                    route = "${Screens.FilmReviewScreen.route}/{movie_id}",
                    arguments = listOf(
                        navArgument("movie_id") { type = NavType.StringType },
                    )
                ) {
                    FilmOverviewScreen(navController = navController)
                }

                composable(
                    route = Screens.CastListScreen.route
                ) {
                    CastListScreen(navController = navController)
                }

                composable(
                    route = Screens.FavoriteListScreen.route
                ) {
                    FavoriteListScreen(navController = navController)
                }

                composable(
                    route = "${Screens.FilmReviewListScreen.route}/{movie_id}",
                    arguments = listOf(
                        navArgument("movie_id") { type = NavType.StringType },
                    )
                ) {
                    FilmReviewListScreen()
                }

                composable(
                    route = "${Screens.ActorReviewScreen.route}/{actor_id}",
                    arguments = listOf(
                        navArgument("actor_id") { type = NavType.StringType },
                    )
                ) {
                    ActorReviewScreen(navController = navController)
                }
            }
        }
    }
}

@Composable
fun TopBar(
    navigationIcon: @Composable (() -> Unit)?,
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = { Text(text = stringResource(R.string.top_app_title)) },
        navigationIcon = navigationIcon,
        backgroundColor = MaterialTheme.colors.onSecondary
    )
}

@Composable
fun BottomNavigationBar (
    navigate: (String) -> Unit,
    isSelected: (String) -> Boolean,
    screens: List<Screens>
) {
    val items = BottomNavItems.values()

    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.onSecondary
    ) {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = stringResource(id = item.text)
                    )
                },
                label = null,
                selected = isSelected(screens[index].route),
                onClick = { navigate(screens[index].route) }
            )
        }
    }
}