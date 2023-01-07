package net.vertexgraphics.my3dart.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ArtElement(@StringRes val stringResourceId: Int,
                      @DrawableRes val imageResourceId: Int)
