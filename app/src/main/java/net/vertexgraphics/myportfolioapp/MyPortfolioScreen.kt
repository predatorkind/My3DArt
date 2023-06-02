package net.vertexgraphics.myportfolioapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import net.vertexgraphics.myportfolioapp.model.GalleryViewModel

import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import net.vertexgraphics.myportfolioapp.ui.GalleryScreen


@Composable
fun MyPortfolioScreen(
    navController: NavHostController = rememberNavController(),
    galleryViewModel: GalleryViewModel = viewModel()
){
    //val galleryViewModel = GalleryViewModel()
    val galleryUiState = galleryViewModel.uiState


    Scaffold(topBar = {
        TopBar(lifetimeTaps = galleryViewModel.lifetimeTaps,
        increaseLifetimeTaps = { galleryViewModel.increaseLifetimeTaps() }
            )
    }) {
        GalleryScreen(galleryViewModel, modifier = Modifier.padding(it))

    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TopBar(modifier: Modifier = Modifier, lifetimeTaps: Int, increaseLifetimeTaps: () -> Unit){
    // save the currentTaps variable between recompositions
    // lifetimeTaps is saved in the GalleryViewModel
    var currentTaps by remember { mutableStateOf(0) }


    Surface(onClick = {
        currentTaps += 1

        increaseLifetimeTaps()

    },
        modifier = modifier){
        Row(modifier = modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(modifier = modifier
                .size(64.dp)
                .padding(8.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null)
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.h1
            )
            Spacer(modifier = Modifier
                .weight(1f)
            )
            Text (
                text = stringResource(R.string.tapCounter, currentTaps, lifetimeTaps),
                style = MaterialTheme.typography.h1,
                modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }

}
