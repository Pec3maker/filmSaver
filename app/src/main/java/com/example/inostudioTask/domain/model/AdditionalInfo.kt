package com.example.inostudioTask.domain.model

import com.example.inostudioTask.data.remote.dto.CreditList
import com.example.inostudioTask.data.remote.dto.ReviewList
import com.example.inostudioTask.data.remote.dto.VideoList

data class AdditionalInfo(
    val credits: CreditList,
    val images: CombinedImages,
    val reviews: ReviewList,
    val videos: VideoList,
)
