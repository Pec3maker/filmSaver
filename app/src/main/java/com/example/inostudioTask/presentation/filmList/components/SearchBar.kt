package com.example.inostudioTask.presentation.filmList.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    text: String,
    onTextChange: (String) -> Unit
) {
    Box(modifier = modifier) {
        OutlinedTextField(
            label = { Text(text = hint) },
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background),
            textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
            singleLine = true,
            maxLines = 1,
            shape = RoundedCornerShape(size = 8.dp)
        )
    }
}