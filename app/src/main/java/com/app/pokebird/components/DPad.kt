package com.app.pokebird.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.pokebird.screens.Direction

@Composable
fun DPad(onDirection: (Direction) -> Unit) {
    Box(
        modifier = Modifier
            .size(140.dp)
    ) {
        // Up
        Button(
            onClick = { onDirection(Direction.Up) },
            modifier = Modifier
                .size(48.dp, 40.dp)
                .align(Alignment.TopCenter),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(Color.DarkGray)
        ) {}

        // Down
        Button(
            onClick = { onDirection(Direction.Down) },
            modifier = Modifier
                .size(48.dp, 40.dp)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(Color.DarkGray)
        ) {}

        // Left
        Button(
            onClick = { onDirection(Direction.Left) },
            modifier = Modifier
                .size(40.dp, 48.dp)
                .align(Alignment.CenterStart),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(Color.DarkGray)
        ) {}

        // Right
        Button(
            onClick = { onDirection(Direction.Right) },
            modifier = Modifier
                .size(40.dp, 48.dp)
                .align(Alignment.CenterEnd),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(Color.DarkGray)
        ) {}

        // Center (just a visual piece, non-clickable)
        Box(
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.Center)
                .background(Color.Gray, RoundedCornerShape(4.dp))
        )
    }
}
