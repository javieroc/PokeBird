package com.app.pokebird

import android.content.Context
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.pokebird.screens.ImageDetail
import com.app.pokebird.screens.MainScreen
import com.app.pokebird.screens.PhotoGallery
import kotlinx.serialization.Serializable
import java.io.File

@Serializable
object Main

@Serializable
object Gallery

@Serializable
data class PhotoDetail(val fileName: String)

@Composable
fun NavigationStack(
    controller: LifecycleCameraController,
    viewModel: MainViewModel,
    context: Context,
    onCapturePhoto: () -> Unit
) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Main) {
        composable<Main> {
            val fileNames by viewModel.fileNames.collectAsState()

            MainScreen(
                controller,
                imageFileNames = fileNames,
                onNavigateToPhotoGallery = {
                    navController.navigate(route = Gallery)
                },
                onCapturePhoto,
                onShowCameraPreview = { viewModel.clearLastCapturedPhoto() }
            )
        }
        composable<Gallery> {
            PhotoGallery(
                viewModel = viewModel,
                onNavigateToMainScreen = {
                    navController.navigate(route = Main)
                },
                onNavigateToImageDetail = { fileName ->
                    navController.navigate(PhotoDetail(fileName))
                }
            )
        }
        composable<PhotoDetail> { backStackEntry ->
            val fileName = backStackEntry.arguments?.getString("fileName") ?: return@composable
            val imageFile = File(context.filesDir, fileName)
            ImageDetail(
                imageFile = imageFile,
                onDelete = {
                    val deleted = viewModel.deletePhotoFile(context, fileName)
                    if (deleted) {
                        navController.popBackStack()
                    }
                },
                onBack = { navController.popBackStack() },
                onSpeak = { /* We'll implement this later */ }
            )
        }

    }
}
