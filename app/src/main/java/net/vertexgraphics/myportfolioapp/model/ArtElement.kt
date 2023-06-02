package net.vertexgraphics.myportfolioapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ArtElement(@StringRes val stringResourceId: Int,
                      @StringRes val descResourceId: Int,
                      @DrawableRes val imageResourceId: Int,
                      var isExpanded: Boolean = false)
