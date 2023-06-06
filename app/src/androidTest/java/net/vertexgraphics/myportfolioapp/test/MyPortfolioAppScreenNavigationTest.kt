package net.vertexgraphics.myportfolioapp.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.vertexgraphics.myportfolioapp.AppScreen
import net.vertexgraphics.myportfolioapp.PortfolioScreens
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import net.vertexgraphics.myportfolioapp.R
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MyPortfolioAppScreenNavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupMyPortfolioNavHost() {
        composeTestRule.setContent {

            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            AppScreen(navController= navController)
        }
    }

    @Test
    fun myPortfolioNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(PortfolioScreens.Start.name)
        //assertEquals(PortfolioScreens.Start.name, navController.currentBackStackEntry?.destination?.route)
    }

    @Test
    fun myPortfolioNavHost_verifyBackNavigationNotShownOnStartOrderScreen()
    {
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }

    @Test
    fun myPortfolioNavHost_clickGallery_navigateToGalleryScreen() {
        navigateToGalleryScreen()
        navController.assertCurrentRouteName(PortfolioScreens.Gallery.name)
    }

    @Test
    fun myPortfolioNavHost_clickUnscrambled_navigateToGameScreen() {
        navigateToGameScreen()
        navController.assertCurrentRouteName(PortfolioScreens.Unscramble.name)
    }

    @Test
    fun myPortfolioNavHost_clickBackOnGallery_navigateToMainMenu() {
        navigateToGalleryScreen()
        navigateBack()
        navController.assertCurrentRouteName(PortfolioScreens.Start.name)
    }

    @Test
    fun myPortfolioNavHost_clickBackOnGame_navigateToMainMenu() {
        navigateToGameScreen()
        navigateBack()
        navController.assertCurrentRouteName(PortfolioScreens.Start.name)
    }


    private fun navigateBack() {
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).performClick()
    }
    private fun navigateToGalleryScreen() {
        composeTestRule.onNodeWithStringId(R.string.gallery).performClick()
    }

    private fun navigateToGameScreen() {
        composeTestRule.onNodeWithStringId(R.string.unscramble).performClick()
    }


}