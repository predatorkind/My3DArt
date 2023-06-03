package net.vertexgraphics.myportfolioapp.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val TAG: String = "MyPortfolioViewModel"

class MyPortfolioViewModel: ViewModel() {
    // MyPortfolioScreen UI State
    private val _uiState = MutableStateFlow(MyPortfolioUiState())

    // Backing property to avoid state changes from other classes
    val uiState: StateFlow<MyPortfolioUiState> = _uiState.asStateFlow()

    var lifetimeTaps by mutableStateOf(0)

    fun increaseLifetimeTaps() {
        /**
         * Increases the lifetime taps by 1
         */
        lifetimeTaps += 1
    }
}