package net.vertexgraphics.myportfolioapp.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import net.vertexgraphics.myportfolioapp.data.Datasource
import net.vertexgraphics.myportfolioapp.ui.GalleryScreen
import org.junit.Rule
import org.junit.Test

class GalleryScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    val artElementList = Datasource().loadImageData()

    @Test
    fun selectGalleryScreen_verifyContent(){
        composeTestRule.setContent {
            GalleryScreen()
        }
        artElementList.forEach {
            artElement ->
            composeTestRule.onNodeWithStringId(artElement.stringResourceId).assertIsDisplayed()

        }

    }


}