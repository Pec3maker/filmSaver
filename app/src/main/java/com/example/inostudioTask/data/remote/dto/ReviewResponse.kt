package com.example.inostudioTask.data.remote.dto

import com.example.inostudioTask.domain.model.Review


data class ReviewResponse(
    val author: String,
    val content: String,
)

fun ReviewResponse.toReview(): Review {
    return Review(
        author = author,
        content = content
    )
}