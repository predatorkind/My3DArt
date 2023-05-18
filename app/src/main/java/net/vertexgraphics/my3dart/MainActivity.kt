package net.vertexgraphics.my3dart


import android.content.ClipDescription
import android.content.Context
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore


import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.vertexgraphics.my3dart.ui.theme.My3DArtTheme
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer

import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import net.vertexgraphics.my3dart.data.Datasource

import net.vertexgraphics.my3dart.model.ArtElement

private const val TAG = "MainActivity"
@ExperimentalMaterial3WindowSizeClassApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)

            My3DArtTheme {

                AppScreen()
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

@Composable
fun AppScreen(){
    var currentTaps by remember { mutableStateOf(0)}
    var lifetimeTaps by rememberSaveable { mutableStateOf(0)}
    Scaffold(topBar = { TopBar()}) {
        ArtList(artElementList = Datasource().loadImageData(), modifier = Modifier.padding(it))
    }
}

@Composable
fun MainScreen(windowSizeClass: WindowSizeClass) {

    var currentImage by remember { mutableStateOf(1)}

    val image = when(currentImage) {
        1 -> R.drawable.de
        2 -> R.drawable.akm
        3 -> R.drawable.kitchen
        4 -> R.drawable.robot
        else -> {currentImage = 0
            R.drawable.logo}
    }
    val context = LocalContext.current
    BoxWithConstraints(modifier = Modifier
        .padding(16.dp)
        .wrapContentSize(align = Alignment.Center, false)
        )
    {
        var scale by remember {mutableStateOf(1f)}
        var rotation by remember { mutableStateOf(0f)}
        var offset by remember { mutableStateOf(Offset.Zero)}
        val state = rememberTransformableState{zoomChange, offsetChange, rotationChange ->
            scale *= zoomChange
            rotation += rotationChange
            offset += offsetChange}
        if (maxHeight < 480.dp) {
            Row() {
                ImageSurface(
                    context = context,
                    scale = scale,
                    rotation = rotation,
                    offset = offset,
                    state = state,
                    image = image,
                    widthFraction = 0.8f,
                    heightFraction = 1f,
                    reset = { zoomChange, rotationChange, offsetChange ->
                        scale = zoomChange
                        rotation = rotationChange
                        offset = offsetChange  }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column() {
                    ImageFlipButton(label = R.string.previous, value = currentImage, onClick = { if (currentImage > 1) currentImage--})
                    ImageFlipButton(label = R.string.next, value = currentImage, onClick = { currentImage++})
                }
            }


        } else {
            Column {
                ImageSurface(
                    context = context,
                    scale = scale,
                    rotation = rotation,
                    offset = offset,
                    state = state,
                    image = image,
                    widthFraction = 1f,
                    heightFraction = 0.8f,
                    reset = { zoomChange, rotationChange, offsetChange ->
                        scale = zoomChange
                        rotation = rotationChange
                        offset = offsetChange
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
                    ImageFlipButton(label = R.string.previous, value = currentImage, onClick = { if (currentImage > 1) currentImage--})
                    ImageFlipButton(label = R.string.next, value = currentImage, onClick = { currentImage++})
            }
        }



        }
    }
    

    
}

@Composable
fun ImageSurface(context: Context, scale: Float, rotation: Float, offset: Offset,
                 state: TransformableState, image: Int, widthFraction: Float,
                 heightFraction: Float, reset: (Float, Float, Offset) -> Unit) {
    Surface(modifier= Modifier
        .padding(0.dp)
        .shadow(
            10.dp, RoundedCornerShape(5.dp), false, Color.Black,
            spotColor = Color.Black
        ),
        shape = RoundedCornerShape(5.dp),
        border = BorderStroke(1.dp, Color.Black),

        color = Color(LocalContext.current.resources.getColor(R.color.green, LocalContext.current.theme))){
        Image(painter = painterResource(id = image), contentDescription = image.toString(),
            modifier = Modifier
                .fillMaxWidth(widthFraction)
                .fillMaxHeight(heightFraction)
                .padding(20.dp)
                .graphicsLayer(
                    scaleX = scale, scaleY = scale, rotationZ = rotation,
                    translationX = offset.x, translationY = offset.y
                )
                .transformable(state = state)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        Toast
                            .makeText(
                                context,
                                context.resources.getString(R.string.image_reset),
                                Toast.LENGTH_SHORT
                            )
                            .show()
                        reset(1f, 0f, Offset.Zero)
                    })
                })

    }
}

@Composable
fun ImageFlipButton(@StringRes label: Int, value: Int, onClick: (Int)->Unit){

    Button(onClick = { onClick(value)}, modifier = Modifier.width(120.dp),
        colors = ButtonDefaults.buttonColors( backgroundColor =
        Color(LocalContext.current.resources.getColor(R.color.green, LocalContext.current.theme)))) {
        Text(text = stringResource(id = label))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TopBar(modifier: Modifier = Modifier){
    var currentTaps by remember { mutableStateOf(0)}
    var lifetimeTaps by rememberSaveable { mutableStateOf(0)}

    Surface(onClick = {
        currentTaps += 1
        lifetimeTaps += 1

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

@Composable
private  fun ArtList(artElementList: List<ArtElement>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 150.dp)) {
        items(items = artElementList) { artElement -> ArtCard(artElement = artElement)
        //items(artElementList){  artElement -> ArtCard(artElement)

        }
    }
}

@Composable
fun ArtCard(artElement: ArtElement, modifier: Modifier = Modifier){
    var fontSizeMultiplier by remember {mutableStateOf(1f)}
    var expanded by remember { mutableStateOf(false)}
    val col by animateColorAsState(targetValue = if (expanded) MaterialTheme.colors.secondary else MaterialTheme.colors.surface) {

    }
    Card(modifier = modifier.padding(8.dp), elevation = 4.dp) {
        Column (modifier = Modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
            .background(color = col)) {
            Image(painter = painterResource(id = artElement.imageResourceId),
                contentDescription = stringResource(id = artElement.stringResourceId),
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp), contentScale = ContentScale.Crop)
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
                    ) {
                Text(text = stringResource(id = artElement.stringResourceId),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(0.7f),
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.h6.copy(fontSize = MaterialTheme.typography.h6.fontSize * fontSizeMultiplier),
                    maxLines = 1,
                    overflow = TextOverflow.Visible,
                    onTextLayout = {
                        if (it.hasVisualOverflow) {
                            fontSizeMultiplier *= 0.95f // you can tune this constant
                        }
                    }
                )
                ArtCardButton(expanded = expanded, onClick = { expanded = !expanded }, modifier = Modifier.fillMaxWidth())
            }
            if (expanded) {
                ArtCardDetails(
                    details = artElement.descResourceId, modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.padding_medium),
                        end = dimensionResource(id = R.dimen.padding_medium),
                        top = dimensionResource(id = R.dimen.padding_small),
                        bottom = dimensionResource(id = R.dimen.padding_medium)
                    )
                )
            }
        }

    }

}

@Composable
private fun ArtCardDetails(@StringRes details: Int, modifier: Modifier ){
    Column(
        modifier = modifier
    ){
        Text(text = stringResource(id = details),
            style = MaterialTheme.typography.body1)
    }
}

@Composable
private fun ArtCardButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        modifier = modifier.width(32.dp),
        onClick = onClick) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore ,
            //painter = painterResource(id = R.drawable.expand_more),
            tint = MaterialTheme.colors.onSurface,
            contentDescription = stringResource(id = R.string.expand_button_content_description)
        )
    }
}


@Preview
@Composable
private fun DarkThemePreview() {
    My3DArtTheme(darkTheme = true) {
        AppScreen()
    }
}

@Preview
@Composable
private fun LightThemePreview() {
    My3DArtTheme(darkTheme = false) {
        AppScreen()
    }
}

@Preview
@Composable
private fun ArtCardPreview() {
    ArtCard(artElement = ArtElement(R.string.Desert_Eagle, R.string.DE_desc, R.drawable.de))
}
//@Preview(showBackground = false)
//@Composable
//fun DefaultPreview() {
//    My3DArtTheme {
//        MainScreen()
//    }
//}
