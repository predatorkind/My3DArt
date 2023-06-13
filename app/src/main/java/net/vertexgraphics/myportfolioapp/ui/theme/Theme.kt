package net.vertexgraphics.myportfolioapp.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import net.vertexgraphics.myportfolioapp.ui.GameScreen

private val DarkColorPalette = darkColorScheme(
    primary = Yellow,

    secondary = GreenDarkSelected,
    surface = Green500,
    background = Green800,
    onSurface = Yellow,
    onBackground = Yellow,
    onPrimary = Color.Black,
    onSecondary = Color.Black,

    onPrimaryContainer = Color.Black,
    secondaryContainer = GreenDarkSelected,
    onSecondaryContainer = Yellow,
    onTertiary = Color.Black,
    onTertiaryContainer = Color.Black,
    surfaceVariant = Green500,
    onSurfaceVariant = Yellow

)

private val LightColorPalette = lightColorScheme(
    primary = Yellow2,

    secondary = GreenSelected,
    surface = Green,
    background = Green500,
    onSurface = Color.White,
    onBackground = Color.White,
    onPrimary = Color.Black,
    onSecondary = Color.White,

    onPrimaryContainer = Color.Black,
    secondaryContainer = GreenSelected,
    onSecondaryContainer = Color.White,
    onTertiary = Color.Black,
    onTertiaryContainer = Color.Black,
    surfaceVariant = Green,
    onSurfaceVariant = Color.White

)

@Composable
fun MyPortfolioAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@Preview(showBackground = true)
@Composable
fun MainPreview(){
    MyPortfolioAppTheme(darkTheme = false) {
        GameScreen()
        
    }
}
@Preview(showBackground = true)
@Composable
fun MainPreviewDark(){
    MyPortfolioAppTheme(darkTheme = true) {
        GameScreen()

    }
}