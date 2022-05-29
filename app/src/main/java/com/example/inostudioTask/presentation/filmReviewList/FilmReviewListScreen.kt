package com.example.inostudioTask.presentation.filmReviewList

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.inostudioTask.R
import com.example.inostudioTask.data.remote.dto.ReviewResponse
import com.example.inostudioTask.presentation.common.ListState
import com.example.inostudioTask.presentation.common.components.ErrorScreen
import com.example.inostudioTask.presentation.ui.theme.Gray150
import com.example.inostudioTask.presentation.ui.theme.Gray300

@Composable
fun FilmReviewListScreen(
    viewModel: FilmReviewListViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (val uiState = viewModel.state.value) {
            is ListState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is ListState.Error -> {
                ErrorScreen(
                    onButtonClick = { viewModel.refresh() },
                    text = uiState.message
                )
            }
            is ListState.Success -> {
                SuccessScreen(data = uiState.data)
            }
            is ListState.Empty -> Unit
        }
    }
}

@Composable
fun SuccessScreen(data: List<ReviewResponse>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        items(data) { item ->
            ReviewItem(item)
            Spacer(modifier = Modifier.height(height = 16.dp))
        }
    }
}

@Composable
fun ReviewItem(item: ReviewResponse) {
    val expanded = remember { mutableStateOf(false) }
    Card(
        border = BorderStroke(width = 1.dp, color = Gray150),
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        modifier = Modifier.fillMaxHeight(),
    ) {
        Column(modifier = Modifier.padding(all = 4.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.author, item.author),
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Start
                )

                Box(
                    contentAlignment = Alignment.TopEnd,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { expanded.value = !expanded.value }
                    ) {
                        Icon(
                            imageVector = if (expanded.value) {
                                Icons.Rounded.ExpandMore
                            } else {
                                Icons.Rounded.ExpandLess
                            },
                            contentDescription = null
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(height = 7.dp))
            Text(
                text = item.content,
                style = MaterialTheme.typography.body1,
                color = Gray300,
                modifier = Modifier.animateContentSize(),
                maxLines = if (expanded.value) Int.MAX_VALUE else 4,
            )
        }
    }
}