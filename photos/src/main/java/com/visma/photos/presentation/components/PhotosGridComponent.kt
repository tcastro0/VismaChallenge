package com.visma.photos.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.visma.domain.features.photocapture.models.Photo

@Composable
fun PhotosGridComponent(photos: LazyPagingItems<Photo>, onItemClick: (String) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 120.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(photos.itemCount) { index ->
            photos[index]?.let {
                Card (
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {onItemClick(it.id)}
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(it.path),
                        contentDescription = null,
                        modifier = Modifier.height(120.dp).fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        photos.apply {
            when(loadState.append){
                 is LoadState.Loading -> {
                    item(span = { GridItemSpan(2) }) {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }
                }
               is LoadState.Error -> {
                    item(span = { GridItemSpan(2) }) {
                        Text("Error loading", color = Color.Red)
                    }
                }

                is LoadState.NotLoading ->  {}
            }
        }
    }

}