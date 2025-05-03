package com.app.pokebird.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AnimatedCircleButton(
    label: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.9f else 1f,
        animationSpec = tween(durationMillis = 100),
        label = "buttonScale"
    )

    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        modifier = Modifier
            .size(64.dp)
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .pointerInteropFilter {
                when (it.action) {
                    android.view.MotionEvent.ACTION_DOWN -> {
                        pressed = true
                        false
                    }
                    android.view.MotionEvent.ACTION_UP,
                    android.view.MotionEvent.ACTION_CANCEL -> {
                        pressed = false
                        false
                    }
                    else -> false
                }
            }
    ) {
        Text(label, fontSize = 30.sp, color = Color.White)
    }
}
