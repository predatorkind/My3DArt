package net.vertexgraphics.myportfolioapp


import android.util.Log
import android.widget.ImageButton
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import net.vertexgraphics.myportfolioapp.model.MyPortfolioViewModel
import net.vertexgraphics.myportfolioapp.ui.GalleryScreen
import net.vertexgraphics.myportfolioapp.ui.GameScreen
import net.vertexgraphics.myportfolioapp.ui.theme.MyPortfolioAppTheme

private const val TAG: String = "MyPortfolioScreen"

enum class PortfolioScreens(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Gallery(title = R.string.gallery),
    Unscramble(title = R.string.unscramble),
    Options(title = R.string.options), //todo
}

@Composable
fun AppScreen(
    navController: NavHostController = rememberNavController(),
    myPortfolioViewModel: MyPortfolioViewModel = viewModel()
){

    val myPortfolioUiState by myPortfolioViewModel.uiState.collectAsState()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = PortfolioScreens.valueOf(
        backStackEntry?.destination?.route ?: PortfolioScreens.Start.name
        )

    Scaffold(topBar = {
        TopBar(
            currentScreen = currentScreen,
            lifetimeTaps = myPortfolioViewModel.lifetimeTaps,
            increaseLifetimeTaps = { myPortfolioViewModel.increaseLifetimeTaps()},
            canNavigateBack = navController.previousBackStackEntry != null,
            navigateUp = { navController.navigateUp()},

            )
    }) {innerPadding ->
        val uiState by myPortfolioViewModel.uiState.collectAsState()
        //GalleryScreen(galleryViewModel, modifier = Modifier.padding(it))
        NavHost(
            navController = navController,
            startDestination = PortfolioScreens.Start.name,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(
                route = PortfolioScreens.Start.name
            ){
                MainMenu(
                    onGalleryButtonClicked = {
                        Log.d(TAG, navController.currentDestination?.route.toString())
                        Log.d(TAG, navController.currentBackStackEntry?.destination?.route.toString())
                        navController.navigate(route = PortfolioScreens.Gallery.name)
                        Log.d(TAG, "Gallery button clicked")
                        Log.d(TAG, navController.currentDestination?.route.toString())
                        Log.d(TAG, navController.currentBackStackEntry?.destination?.route.toString())
                    },
                    onUnscrambledButtonClicked = {
                        navController.navigate(route = PortfolioScreens.Unscramble.name)
                        Log.d(TAG, "Unscramble button clicked")
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }
            composable(
                route = PortfolioScreens.Gallery.name
            ) {
                val context = LocalContext.current
                GalleryScreen()
            }
            composable(
                route = PortfolioScreens.Unscramble.name
            ) {
                GameScreen()
            }
        }
    }
}

@Composable
private fun MainMenu(
    modifier: Modifier = Modifier,
    onGalleryButtonClicked: () -> Unit,
    onUnscrambledButtonClicked: () -> Unit,
    
    ){
    Column(
        modifier= Modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,


    ) {
        Button(
            onClick = { onGalleryButtonClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_big))
                .testTag("go_to_gallery"),
        ) {
            Text(text = stringResource(id = R.string.gallery))
        }
        Button(
            onClick = { onUnscrambledButtonClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_big)),
        ) {
            Text(text = stringResource(id = R.string.unscramble))
        }
    }
}




@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    lifetimeTaps: Int,
    increaseLifetimeTaps: () -> Unit,
    currentScreen: PortfolioScreens,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},


    ){
    // save the currentTaps variable between recompositions
    // lifetimeTaps is saved in the GalleryViewModel
    var currentTaps by remember { mutableStateOf(0) }


    TopAppBar(
        modifier = modifier,
        contentPadding = PaddingValues(all = dimensionResource(id = R.dimen.padding_small)),

    ){
//        Row(modifier = modifier
//            .fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically
//        ){

            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button),
                    )
                }
            }

            Image(
                modifier = modifier
                    .size(64.dp)
                    .padding(8.dp)
                    .clickable {
                        currentTaps++
                        increaseLifetimeTaps()
                    },
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null)
            Text(
                text = stringResource(id = currentScreen.title),
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier
                .weight(1f)
            )
            Text (
                text = stringResource(R.string.tapCounter, currentTaps, lifetimeTaps),
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_medium))
            )
        //}
    }

}

@Preview(showBackground = true)
@Composable
fun MainMenuPreview() {
    MyPortfolioAppTheme() {
        TopAppBar() {
            
        }
    }
}
