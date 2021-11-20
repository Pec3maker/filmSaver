package com.example.inostudioTask.data.remote.dto

import com.example.inostudioTask.domain.model.AdditionalInfo

data class AdditionalInfoResponse(
    val credits: CreditList,
    val images: ImageList,
    val reviews: ReviewList,
    val videos: VideoList,
)

fun AdditionalInfoResponse.toAdditionalInfo(): AdditionalInfo {
    return AdditionalInfo(
        credits = credits,
        images = images.toCombinedImages(),
        reviews = reviews,
        videos = videos
    )
}