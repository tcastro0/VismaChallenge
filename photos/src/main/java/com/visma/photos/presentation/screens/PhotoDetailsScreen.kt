package com.visma.photos.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.visma.photos.presentation.components.BigPhotoCardComponent
import com.visma.photos.presentation.components.PhotoDetailExpenseComponent
import com.visma.photos.presentation.components.PhotoDetailTopBar
import com.visma.photos.presentation.viewmodels.PhotoDetailsViewModel

@Composable
fun PhotoDetailsScreen(
    viewModel: PhotoDetailsViewModel,
    id: String,
    onBackClick: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.loadData(id)
    }
    val scrollState = rememberScrollState()
    val state = viewModel.state.collectAsState()
    val photo by remember(state) { derivedStateOf { state.value.photo } }
    val expense by remember(state) { derivedStateOf { state.value.detail } }


    Scaffold(
        topBar = {
            PhotoDetailTopBar(photo?.filename ?: "", onBackClick)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            if (state.value.isLoading) {
                LinearProgressIndicator()
            }
            Spacer(modifier = Modifier.padding(8.dp))
            photo?.let {
                Box(Modifier
                    .fillMaxWidth()
                    .height(300.dp)) {
                    BigPhotoCardComponent(it)
                }
            }
            Spacer(modifier = Modifier.padding(16.dp))

            expense?.let {
                PhotoDetailExpenseComponent(it)
            } ?: kotlin.run {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Warning, contentDescription = "Warning")
                    Text(text = "No data found")
                }
            }

        }


    }

}