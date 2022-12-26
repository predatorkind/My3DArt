package net.vertexgraphics.my3dart


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.vertexgraphics.my3dart.ui.theme.My3DArtTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            My3DArtTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(resources.getColor(R.color.green_500, theme))
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    var currentImage by remember { mutableStateOf(1)}

    val image = when(currentImage) {
        1 -> R.drawable.de
        2 -> R.drawable.akm
        3 -> R.drawable.kitchen
        4 -> R.drawable.robot
        else -> {currentImage = 0
            R.drawable.logo}
    }
    Column(modifier = Modifier
        .padding(16.dp)
        .wrapContentSize(align = Alignment.Center)
        ) {
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
                    .size(400.dp)
                    .padding(20.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
            ImageFlipButton(label = R.string.previous, value = currentImage, onClick = { if (currentImage > 1) currentImage--})
            ImageFlipButton(label = R.string.next, value = currentImage, onClick = { currentImage++})
        }
    }
    

    
}


@Composable
fun ImageFlipButton(@StringRes label: Int, value: Int, onClick: (Int)->Unit){
    Button(onClick = { onClick(value) }, modifier = Modifier.width(120.dp),
        colors = ButtonDefaults.buttonColors( backgroundColor =
        Color(LocalContext.current.resources.getColor(R.color.green, LocalContext.current.theme)))) {
        Text(text = stringResource(id = label))
    }
}


@Preview(showBackground = false)
@Composable
fun DefaultPreview() {
    My3DArtTheme {
        MainScreen()
    }
}