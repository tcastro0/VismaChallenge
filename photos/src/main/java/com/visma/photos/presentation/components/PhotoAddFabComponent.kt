package com.visma.photos.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable


@Composable
fun PhotoAddFabComponent(onAddPhotoClick: () -> Unit) {
    FloatingActionButton(
        onClick = onAddPhotoClick,
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(Icons.Default.AddAPhoto, contentDescription = "Add Photo")
    }
}