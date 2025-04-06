package com.visma.photos.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.visma.domain.core.VismaResult
import com.visma.domain.features.photocapture.usecases.GetExpensesByPhotoUseCase
import com.visma.domain.features.photos.usecases.GetPhotoByIdUseCase
import com.visma.photos.presentation.state.PhotoDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PhotoDetailsViewModel @Inject constructor(
    val getExpensesByPhotoUseCase: GetExpensesByPhotoUseCase,
    val getPhotoByIdUseCase: GetPhotoByIdUseCase
) : ViewModel() {

    var state = MutableStateFlow<PhotoDetailState>(PhotoDetailState(isLoading = true))
        private set

    fun loadData(id: String) {
        viewModelScope.launch {
            launch(Dispatchers.IO) {
                val result = getPhotoByIdUseCase(id)
                when (result) {
                    is VismaResult.Error -> {
                        state.value = state.value.copy(isLoading = false, error = result.message)
                    }

                    is VismaResult.Success -> {
                        state.value =
                            state.value.copy(isLoading = false, photo = result.result)
                    }
                }
            }
            launch(Dispatchers.IO) {
                val result = getExpensesByPhotoUseCase(id)
                when (result) {
                    is VismaResult.Error -> {}
                    is VismaResult.Success -> {
                        result.result.catch {
                            state.value = state.value.copy(isLoading = false, error = it.message)
                        }.collect { list ->
                            state.value =
                                state.value.copy(isLoading = false, detail = list.firstOrNull())
                        }
                    }
                }
            }

        }


    }

}