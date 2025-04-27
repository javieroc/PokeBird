package com.app.pokebird

import android.graphics.Bitmap
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.pokebird.screens.MainScreen
import com.app.pokebird.screens.PhotoGallery
import kotlinx.serialization.Serializable

@Serializable
object Main

@Serializable
object Gallery

@Composable
fun NavigationStack(
    controller: LifecycleCameraController,
    viewModel: MainViewModel,
    onCapturePhoto: () -> Unit
) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Main) {
        composable<Main> {
            MainScreen(
                controller,
                onNavigateToPhotoGallery = {
                    navController.navigate(route = Gallery)
                },
                onCapturePhoto,
            )
        }
        composable<Gallery> {
            val images by viewModel.bitmaps.collectAsState()
            PhotoGallery(bitmaps = images, modifier = Modifier)
        }
    }
}
