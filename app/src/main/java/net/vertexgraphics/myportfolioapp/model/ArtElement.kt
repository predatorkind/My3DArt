package net.vertexgraphics.myportfolioapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class ArtElement(@StringRes val stringResourceId: Int,
                      @StringRes val descResourceId: Int,
                      @DrawableRes val imageResourceId: Int,
                      val initialExpanded: Boolean = false,
) {
    var isExpanded by mutableStateOf(initialExpanded)
}


