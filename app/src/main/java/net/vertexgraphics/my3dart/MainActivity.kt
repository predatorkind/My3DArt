package net.vertexgraphics.my3dart


import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*

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
import androidx.compose.ui.layout.ContentScale
import net.vertexgraphics.my3dart.data.Datasource

import net.vertexgraphics.my3dart.model.ArtElement

@ExperimentalMaterial3WindowSizeClassApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)

            My3DArtTheme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(resources.getColor(R.color.green_500, theme))
                ) {
                    // MainScreen(windowSizeClass)
                    //TODO make use of widnowsizeclass
                    ArtList(artElementList = Datasource().loadImageData())
                }
            }
        }
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

@Composable
private  fun ArtList(artElementList: List<ArtElement>, modifier: Modifier = Modifier) {
    LazyColumn() {
        items(artElementList){  artElement -> ArtCard(artElement)

        }
    }
}

@Composable
fun ArtCard(artElement: ArtElement, modifier: Modifier = Modifier){
    Card(modifier = Modifier.padding(8.dp), elevation = 4.dp) {
        Column {
            Image(painter = painterResource(id = artElement.imageResourceId),
                contentDescription = stringResource(id = artElement.stringResourceId),
            modifier = Modifier
                .fillMaxWidth()
                .height(194.dp), contentScale = ContentScale.Crop)
            Text(text = stringResource(id = artElement.stringResourceId),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.h6
            )
        }

    }

}

@Preview
@Composable
private fun ArtCardPreview() {
    ArtCard(artElement = ArtElement(R.string.Desert_Eagle, R.drawable.de))
}
//@Preview(showBackground = false)
//@Composable
//fun DefaultPreview() {
//    My3DArtTheme {
//        MainScreen()
//    }
//}