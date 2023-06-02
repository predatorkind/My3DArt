package net.vertexgraphics.myportfolioapp.model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.vertexgraphics.myportfolioapp.data.Datasource


private const val TAG = "GalleryViewModel"

class GalleryViewModel: ViewModel() {
    // Gallery UI State
    private val _uiState = MutableStateFlow(GalleryUiState())

    // Backing property to avoid state changes from other classes
    val uiState: StateFlow<GalleryUiState> = _uiState.asStateFlow()

    var lifetimeTaps by mutableStateOf(0)

    // ArtElement list
    val _artElementList = mutableStateListOf<ArtElement>()
    val artElementList: List<ArtElement> = _artElementList





    init {
        _artElementList.addAll(Datasource().loadImageData())
    }




    fun toggleArtElementExpanded(artElement: ArtElement) {
        /**
         * Sets the expanded state of the ArtElement
         */

        val indx = _artElementList.indexOf(artElement)
        _artElementList[indx].isExpanded = !_artElementList[indx].isExpanded

        Log.d(TAG, "setExpandedState: ${artElement.isExpanded} index: ${indx}")
    }

    fun increaseLifetimeTaps() {
        /**
         * Increases the lifetime taps by 1
         */
        lifetimeTaps += 1
    }
}