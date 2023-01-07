package net.vertexgraphics.my3dart.data

import net.vertexgraphics.my3dart.R
import net.vertexgraphics.my3dart.model.ArtElement

class Datasource {
    fun loadImageData(): List<ArtElement> {
        return listOf(ArtElement(R.string.Desert_Eagle, R.drawable.de),
        ArtElement(R.string.AKM, R.drawable.akm),
        ArtElement(R.string.kitchen, R.drawable.kitchen),
        ArtElement(R.string.robot, R.drawable.robot),
        ArtElement(R.string.logo, R.drawable.logo),)

    }
}