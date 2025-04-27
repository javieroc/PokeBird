package com.app.pokebird.screens

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.pokebird.components.CameraPreview
import com.app.pokebird.components.DPad


@Composable
fun MainScreen(
        controller: LifecycleCameraController,
        onNavigateToPhotoGallery: () -> Unit,
        onCapturePhoto: () -> Unit,
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
            CameraPreview(
                controller = controller,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
            )
        }

        Row(
            modifier = Modifier
                .padding(top = 46.dp),
                // .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DPad(onDirection = { direction ->
                // Handle direction input
            })

            Column(
                modifier = Modifier
                    .padding(start = 46.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { onCapturePhoto() },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00C3E3),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.size(64.dp)
                ) {
                    Text("A", fontSize = 30.sp)
                }
                Button(
                    onClick = { onNavigateToPhotoGallery() },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF414548),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.size(64.dp)
                ) {
                    Text("B", fontSize = 30.sp)
                }
            }
        }
    }
}
