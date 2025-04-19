package com.app.pokebird

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

@Composable
fun AppUI(controller: LifecycleCameraController) {
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
                .border(30.dp, Color.LightGray, RoundedCornerShape(16.dp))
        ) {
            CameraPreview(
                controller = controller,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
            )
        }

        // Buttons
        Row(
            modifier = Modifier
                .padding(48.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { /* Capture logic */ },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00C3E3),
                    contentColor = Color.White
                ),
                modifier = Modifier.size(64.dp)
            ) {
                Text("A", color = Color.White, fontSize = 30.sp)
            }
            Button(
                onClick = { /* Cancel logic */ },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF414548),
                    contentColor = Color.White
                ),
                modifier = Modifier.size(64.dp)
            ) {
                Text("B", color = Color.White, fontSize = 30.sp)
            }
        }
    }
}
