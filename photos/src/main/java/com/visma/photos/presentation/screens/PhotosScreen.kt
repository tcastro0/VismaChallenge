package com.visma.photos.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.paging.compose.collectAsLazyPagingItems
import com.visma.photos.presentation.components.PhotosGridComponent
import com.visma.photos.presentation.viewmodels.PhotosViewModel

@Composable
fun PhotosScreen(
    viewModel: PhotosViewModel,
    onAddPhotoClick: () -> Unit,
    onItemClick: (String) -> Unit,
    fab: (@Composable () -> Unit) -> Unit
) {
    val state = viewModel.state.collectAsState()
    val photos = state.value.data.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        fab {

        }
    }

    PhotosGridComponent(photos,onItemClick)


}