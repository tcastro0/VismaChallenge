package com.visma.photocapture.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.visma.domain.features.photocapture.usecases.SavePhotoCaptureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PhotoCaptureViewModel @Inject constructor(
    private val savePhotoUseCase: SavePhotoCaptureUseCase
) : ViewModel() {

    fun saveImage(imagePath: String) {
        viewModelScope.launch {
            savePhotoUseCase(imagePath)
        }
    }
}