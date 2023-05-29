package net.vertexgraphics.myportfolioapp.model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val TAG = "GalleryViewModel"

class GalleryViewModel: ViewModel() {
    // Gallery UI State
    private val _uiState = MutableStateFlow(GalleryUiState())

    // Backing property to avoid state changes from other classes
    val uiState: StateFlow<GalleryUiState> = _uiState.asStateFlow()

    var lifetimeTaps by mutableStateOf(0)

    fun increaseLifetimeTaps() {
        Log.d(TAG, "increaselifetimeTaps called")
        lifetimeTaps += 1
    }
}