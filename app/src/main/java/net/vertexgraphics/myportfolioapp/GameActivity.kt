package net.vertexgraphics.myportfolioapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import net.vertexgraphics.myportfolioapp.ui.GameScreen
import net.vertexgraphics.myportfolioapp.ui.theme.MyPortfolioAppTheme

class GameActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPortfolioAppTheme {
                GameScreen()
            }
        }
    }
}