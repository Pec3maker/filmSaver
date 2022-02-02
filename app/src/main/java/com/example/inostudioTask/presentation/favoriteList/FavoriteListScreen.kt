package com.example.inostudioTask.presentation.favoriteList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.inostudioTask.presentation.common.Tabs
import com.example.inostudioTask.presentation.favoriteList.actorList.ActorListScreen
import com.example.inostudioTask.presentation.favoriteList.filmList.FilmListScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun FavoriteListScreen(
    navController: NavController,
) {

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val items = Tabs.values()

    Box(Modifier.fillMaxSize()) {
        Column {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                backgroundColor = MaterialTheme.colors.background
            ) {
                items.forEachIndexed { index, tab ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        icon = {
                            Icon(imageVector = tab.icon, contentDescription = null)
                        },
                        text = {
                            Text(text = stringResource(id = tab.text))
                        }
                    )
                }
            }

            HorizontalPager(
                count = items.size,
                state = pagerState,
            ) { page ->
                if (page == items.indexOf(Tabs.FILMS)) {
                    FilmListScreen(navController = navController)
                } else {
                    ActorListScreen(navController = navController)
                }
            }
        }
    }
}