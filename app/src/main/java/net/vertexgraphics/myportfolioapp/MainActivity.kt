package net.vertexgraphics.myportfolioapp


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import net.vertexgraphics.myportfolioapp.ui.GalleryScreen
import net.vertexgraphics.myportfolioapp.ui.GameScreen

import net.vertexgraphics.myportfolioapp.ui.theme.MyPortfolioAppTheme


private const val TAG = "MainActivity"
@ExperimentalMaterial3WindowSizeClassApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)

            MyPortfolioAppTheme {

                MyPortfolioScreen()
                //GameScreen()
                //GalleryScreen()

            }
        }
        Log.d(TAG, "onCreate Called")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart Called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop Called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume Called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy Called")
    }

}




