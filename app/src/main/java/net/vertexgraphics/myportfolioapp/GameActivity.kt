package net.vertexgraphics.myportfolioapp

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import net.vertexgraphics.myportfolioapp.ui.GameScreen
import net.vertexgraphics.myportfolioapp.ui.theme.My3DArtTheme

class GameActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            My3DArtTheme {
                GameScreen()
            }
        }
    }
}