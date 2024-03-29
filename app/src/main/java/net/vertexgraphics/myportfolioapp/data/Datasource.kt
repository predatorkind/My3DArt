package net.vertexgraphics.myportfolioapp.data

import net.vertexgraphics.myportfolioapp.R
import net.vertexgraphics.myportfolioapp.model.ArtElement

class Datasource {
    fun loadImageData(): List<ArtElement> {
        return listOf(ArtElement(R.string.Desert_Eagle, R.string.DE_desc, R.drawable.de),
        ArtElement(R.string.AKM, R.string.AKM_desc, R.drawable.akm),
        ArtElement(R.string.kitchen, R.string.Kitchen_desc, R.drawable.kitchen),
        ArtElement(R.string.robot, R.string.Robot_desc, R.drawable.robot),
        ArtElement(R.string.logo, R.string.Logo_desc, R.drawable.logo),)

    }
}