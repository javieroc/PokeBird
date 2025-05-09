package com.app.pokebird

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.pokebird.ui.theme.PokeBirdTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this, CAMERAX_PERMISSIONS, 0
            )
        }
        setContent {
            PokeBirdTheme {
                val controller = remember {
                    LifecycleCameraController(applicationContext).apply {
                        setEnabledUseCases(
                            CameraController.IMAGE_CAPTURE or
                                    CameraController.VIDEO_CAPTURE
                        )
                    }
                }
                val viewModel = viewModel<MainViewModel>()
                LaunchedEffect(Unit) {
                    viewModel.loadPhotoFileNames(applicationContext)
                }

                NavigationStack(
                    controller,
                    viewModel,
                    applicationContext,
                    onCapturePhoto = {
                        takePhoto(controller, viewModel)
                    }
                )
            }
        }
    }

    private fun takePhoto(
        controller: LifecycleCameraController,
        viewModel: MainViewModel,
    ) {
        controller.takePicture(
            ContextCompat.getMainExecutor(applicationContext),
            object : OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)

                    lifecycleScope.launch {
                        val rotatedBitmap = withContext(Dispatchers.Default) {
                            val matrix = Matrix().apply {
                                postRotate(image.imageInfo.rotationDegrees.toFloat())
                            }
                            Bitmap.createBitmap(
                                image.toBitmap(),
                                0,
                                0,
                                image.width,
                                image.height,
                                matrix,
                                true
                            )
                        }

                        viewModel.onTakePhoto(applicationContext, rotatedBitmap)

                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                applicationContext,
                                "Picture taken!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    Log.e("Camera", "Couldn't capture image", exception)
                }
            }
        )
    }

    private fun hasRequiredPermissions(): Boolean {
        return CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        private val CAMERAX_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
        )
    }
}
