package com.visma.photos.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.visma.domain.features.photos.usecases.GetPhotosUseCase
import com.visma.photos.presentation.state.PhotosScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase
) : ViewModel() {

    var state = MutableStateFlow<PhotosScreenState>(PhotosScreenState(isLoading = true))
        private set

    init {
        loadExpenses()
    }

    private fun loadExpenses() {
        viewModelScope.launch {
            val flow =  getPhotosUseCase().cachedIn(viewModelScope).catch {
                state.value = state.value.copy(isLoading = false, error = it.message)
            }

            state.update {
                it.copy(isLoading = false, data = flow)
            }
        }
    }

}