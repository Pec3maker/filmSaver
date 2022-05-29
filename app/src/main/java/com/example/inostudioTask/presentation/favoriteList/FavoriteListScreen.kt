package com.example.inostudioTask.presentation.favoriteList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.inostudioTask.presentation.common.Tabs
import com.example.inostudioTask.presentation.favoriteList.actorList.ActorListScreen
import com.example.inostudioTask.presentation.favoriteList.filmList.FilmListScreen
import com.example.inostudioTask.presentation.ui.theme.Gray200
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
                backgroundColor = MaterialTheme.colors.background,
                indicator = {
                    TabRowDefaults.Indicator(
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.tabIndicatorOffset(it[pagerState.currentPage])
                    )
                }
            ) {
                items.forEachIndexed { index, tab ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                text = stringResource(id = tab.text),
                                style = MaterialTheme.typography.h6,
                                color = Gray200
                            )
                        },
                        selectedContentColor = MaterialTheme.colors.primary
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