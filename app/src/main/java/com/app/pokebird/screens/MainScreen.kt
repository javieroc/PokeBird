package com.app.pokebird.screens

import android.content.Context
import android.graphics.Bitmap
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.pokebird.components.AnimatedCircleButton
import com.app.pokebird.components.CameraPreview
import com.app.pokebird.components.DPad
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import java.io.File


enum class CameraMode {
    Camera,
    PhotoPreview
}

enum class Direction {
    Up, Down, Left, Right
}

@Composable
fun MainScreen(
    controller: LifecycleCameraController,
    imageFileNames: List<String>,
    onNavigateToPhotoGallery: () -> Unit,
    onCapturePhoto: () -> Unit,
    onShowCameraPreview: () -> Unit
    ) {
    val context = LocalContext.current
    var mode by remember { mutableStateOf(CameraMode.Camera) }
    var currentPhotoIndex by remember { mutableIntStateOf(imageFileNames.lastIndex) }

    val currentPhotoFile = imageFileNames.getOrNull(currentPhotoIndex)
    val photoFile = File(context.filesDir, currentPhotoFile ?: "")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffff4554)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(width = 360.dp, height = 480.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(16.dp, Color.LightGray, RoundedCornerShape(12.dp))
                .background(Color(0xff414548))
        ) {
            when (mode) {
                CameraMode.Camera -> CameraPreview(
                    controller = controller,
                    modifier = Modifier.fillMaxSize()
                )

                CameraMode.PhotoPreview -> AsyncImage(
                    model = photoFile,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(top = 46.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DPad(onDirection = { direction ->
                when (direction) {
                    Direction.Up -> {
                        mode = CameraMode.Camera
                        onShowCameraPreview()
                    }

                    Direction.Down -> {
                        currentPhotoIndex = imageFileNames.lastIndex
                        mode = CameraMode.PhotoPreview
                    }

                    Direction.Left -> {
                        if (currentPhotoIndex > 0) {
                            currentPhotoIndex--
                            mode = CameraMode.PhotoPreview
                        }
                    }

                    Direction.Right -> {
                        if (currentPhotoIndex < imageFileNames.lastIndex) {
                            currentPhotoIndex++
                            mode = CameraMode.PhotoPreview
                        }
                    }
                }
            })

            Column(
                modifier = Modifier
                    .padding(start = 46.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedCircleButton(
                    label = "A",
                    backgroundColor = Color(0xFF00C3E3),
                    onClick = onCapturePhoto
                )
                AnimatedCircleButton(
                    label = "B",
                    backgroundColor = Color(0xFF414548),
                    onClick = onNavigateToPhotoGallery
                )
            }
        }
    }
}
