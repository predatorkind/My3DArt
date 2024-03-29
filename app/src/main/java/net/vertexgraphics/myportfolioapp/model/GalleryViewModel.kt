package net.vertexgraphics.myportfolioapp.model

import android.util.Log
import androidx.compose.runtime.toMutableStateList
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



    // ArtElement list
    private val _artElementList = Datasource().loadImageData().toMutableStateList()
    val artElementList: List<ArtElement> = _artElementList





    init {

        Log.d(TAG, "Initializing")
    }




    fun toggleArtElementExpanded(artElement: ArtElement) {
        /**
         * Sets the expanded state of the ArtElement
         */

        val indx = _artElementList.indexOf(artElement)
        _artElementList

        _artElementList[indx].isExpanded = !_artElementList[indx].isExpanded


        Log.d(TAG, "setExpandedState: ${artElement.isExpanded} index: ${indx}")
    }


}