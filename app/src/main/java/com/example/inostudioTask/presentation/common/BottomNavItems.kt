package com.example.inostudioTask.presentation.common

import androidx.annotation.DrawableRes
import com.example.inostudioTask.R

enum class BottomNavItems(@DrawableRes val icon: Int, val route: String, val title: String) {
    FILMS(icon = R.drawable.ic_search, route = "films", title = "Search"),
    CAST(icon = R.drawable.ic_cast, route = "cast", title = "Actors"),
    FAVORITES(icon = R.drawable.ic_like, route = "favorites", title = "Favorites")
}