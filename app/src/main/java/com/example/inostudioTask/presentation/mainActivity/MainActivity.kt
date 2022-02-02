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
import androidx.compose.ui.unit.dp
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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

@ExperimentalPagerApi
@ExperimentalAnimationApi
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
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
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
            TopBar(
                navigationIcon =
                if (
                    BottomNavItems.values().none {
                        currentDestination?.route == currentDestination?.parent?.startDestinationRoute
                    }
                ) {
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
                },
                title = Screens.fromRoute(currentDestination?.route).title
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
                startDestination = BottomNavItems.FILMS.route
            ) {
                filmsGraph(navController = navController, scaffoldState = scaffoldState)
                castGraph(navController = navController)
                favoritesGraph(navController = navController)
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
fun NavGraphBuilder.filmsGraph(navController: NavController, scaffoldState: ScaffoldState) {
    navigation(
        startDestination = Screens.FilmsListScreen.route,
        route = BottomNavItems.FILMS.route
    ) {

        composable(
            route = Screens.FilmsListScreen.route
        ) {
            FilmListScreen(navController = navController) {
                scaffoldState.snackbarHostState.showSnackbar(it)
            }
        }

        composable(
            route = Screens.FilmReviewScreen.route,
            arguments =  Screens.FilmReviewScreen.arguments
        ) {
            FilmOverviewScreen(navController = navController)
        }

        composable(
            route = Screens.FilmReviewListScreen.route,
            arguments = Screens.FilmReviewListScreen.arguments
        ) {
            FilmReviewListScreen()
        }
    }
}

@ExperimentalPagerApi
fun NavGraphBuilder.castGraph(navController: NavController) {
    navigation(
        startDestination = Screens.CastListScreen.route,
        route = BottomNavItems.CAST.route
    ) {

        composable(
            route = Screens.CastListScreen.route
        ) {
            CastListScreen(navController = navController)
        }

        composable(
            route = Screens.ActorReviewScreen.route,
            arguments = Screens.ActorReviewScreen.arguments
        ) {
            ActorReviewScreen(navController = navController)
        }
    }
}

@ExperimentalPagerApi
fun NavGraphBuilder.favoritesGraph(navController: NavController) {
    navigation(
        startDestination = Screens.FavoriteListScreen.route,
        route = BottomNavItems.FAVORITES.route
    ) {
        composable(
            route = Screens.FavoriteListScreen.route
        ) {
            FavoriteListScreen(navController = navController)
        }
    }
}

@Composable
fun TopBar(
    navigationIcon: @Composable (() -> Unit)?,
    title: String
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = { Text(text = title) },
        navigationIcon = navigationIcon,
        backgroundColor = MaterialTheme.colors.onSecondary
    )
}

@Composable
fun BottomNavigationBar (
    navigate: (String) -> Unit,
    isSelected: (String) -> Boolean
) {
    val items = BottomNavItems.values()

    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.onSecondary
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.route
                    )
                },
                label = null,
                selected = isSelected(item.route),
                onClick = { navigate(item.route) }
            )
        }
    }
}