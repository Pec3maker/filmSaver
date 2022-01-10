package com.example.inostudioTask.presentation.common.components

import com.example.inostudioTask.R

fun getLikedItemText(isInDatabase: Boolean): Int {
    if (isInDatabase) {
        return R.string.delete_favorite
    }
    return R.string.add_favorite
}