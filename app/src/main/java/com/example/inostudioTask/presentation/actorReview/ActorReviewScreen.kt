package com.example.inostudioTask.presentation.actorReview

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ActorReviewScreen(
    viewModel: ActorReviewViewModel = hiltViewModel()
) {
    Text("ActorReviewScreen")
}