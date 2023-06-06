package net.vertexgraphics.myportfolioapp.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import net.vertexgraphics.myportfolioapp.AppScreen
import net.vertexgraphics.myportfolioapp.ui.GameScreen

private val DarkColorPalette = darkColors(
    primary = Yellow,

    secondary = GreenDarkSelected,
    surface = Green500,
    background = Green800,
    onSurface = Yellow,
    onBackground = Yellow,
    onPrimary = Color.Black,
    onSecondary = Color.Black,

)

private val LightColorPalette = lightColors(
    primary = Yellow2,

    secondary = GreenSelected,
    surface = Green,
    background = Green500,
    onSurface = Color.White,
    onBackground = Color.White,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    /* Other default colors to override
    ,

    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MyPortfolioAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@Preview(showBackground = true)
@Composable
fun mainPreview(){
    MyPortfolioAppTheme(darkTheme = false) {
        GameScreen()
        
    }
}
@Preview(showBackground = true)
@Composable
fun mainPreviewDark(){
    MyPortfolioAppTheme(darkTheme = true) {
        GameScreen()

    }
}