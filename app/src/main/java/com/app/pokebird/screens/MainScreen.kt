package com.app.pokebird.screens

import android.graphics.Bitmap
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.app.pokebird.components.AnimatedCircleButton
import com.app.pokebird.components.CameraPreview
import com.app.pokebird.components.DPad


@Composable
fun MainScreen(
    controller: LifecycleCameraController,
    lastPhoto: Bitmap?,
    onNavigateToPhotoGallery: () -> Unit,
    onCapturePhoto: () -> Unit,
    onShowCameraPreview: () -> Unit
    ) {
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
                .border(20.dp, Color.LightGray, RoundedCornerShape(16.dp))
        ) {
            if (lastPhoto != null) {
                Image(
                    bitmap = lastPhoto.asImageBitmap(),
                    contentDescription = "Last captured photo",
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                CameraPreview(
                    controller = controller,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(top = 46.dp),
                // .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DPad(onDirection = { direction ->
                if (direction == "UP") onShowCameraPreview()
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
